/*
 * Copyright © 2017 Cask Data, Inc.
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

package io.cdap.cdap.app.deploy.spark

import java.net.URL

import io.cdap.cdap.api.spark.Spark
import io.cdap.cdap.api.spark.dynamic.SparkCompiler
import io.cdap.cdap.app.runtime.spark.dynamic.{DefaultSparkCompiler, URLAdder}
import io.cdap.cdap.common.id.Id
import io.cdap.cdap.internal.app.runtime.artifact.ArtifactRepository
import io.cdap.cdap.internal.app.runtime.plugin.PluginInstantiator

import scala.reflect.io.VirtualDirectory
import scala.tools.nsc.Settings

/**
  * The default implementation of [[io.cdap.cdap.api.spark.ExtendedSparkConfigurer]].
  */
class DefaultExtendedSparkConfigurer(spark: Spark,
                                     deployNamespace: Id.Namespace,
                                     artifactId: Id.Artifact,
                                     artifactRepository: ArtifactRepository,
                                     pluginInstantiator: PluginInstantiator)
  extends AbstractExtendedSparkConfigurer(spark, deployNamespace, artifactId, artifactRepository, pluginInstantiator) {

  override def createSparkCompiler(settings: Settings): SparkCompiler = {
    // Creates an in-memory compiler
    new DefaultSparkCompiler(settings, new VirtualDirectory("<memory>", None), new URLAdder {
      override def addURLs(urls: URL*): Unit = {
        // no-op
      }}, () => { /* no-op */ })
  }
}
