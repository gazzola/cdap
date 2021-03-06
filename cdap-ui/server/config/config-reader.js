/*
 * Copyright © 2019 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
*/

const q = require('q');
const spawn = require('child_process').spawn;
const StringDecoder = require('string_decoder').StringDecoder;
const decoder = new StringDecoder('utf8');
const log4js = require('log4js');
const nodepath = require('path');
var log = log4js.getLogger('default');

class ConfigReader {
  constructor(param) {
    this.buffer = '';
    this.deferred = q.defer();
    this.tool = spawn(nodepath.join(__dirname, 'bin', 'cdap'), ['config-tool', '--' + param]);
    this.tool.stderr.on('data', this.configReadFail.bind(this));
    this.tool.stdout.on('data', this.configRead.bind(this));
    this.tool.stdout.on('end', this.onConfigReadEnd.bind(this, param));
  }
  configReadFail(data) {
    var textChunk = decoder.write(data);
    if (textChunk) {
      log.error(textChunk);
    }
  }
  configRead(data) {
    try {
      var textChunk = decoder.write(data);
      if (textChunk) {
        this.buffer += textChunk;
      }
    } catch (e) {
      log.error('Error while reading config: ' + e);
    }
  }
  onConfigReadEnd() {
    let result;
    try {
      result = JSON.parse(this.buffer);
    } catch (e) {
      log.error('Error parsing configuration: ' + e);
      this.deferred.reject(e);
    }
    this.deferred.resolve(result);
  }
  getPromise() {
    return this.deferred.promise;
  }
}

module.exports = ConfigReader;
