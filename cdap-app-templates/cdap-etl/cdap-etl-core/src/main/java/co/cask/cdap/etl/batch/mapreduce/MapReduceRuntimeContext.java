/*
 * Copyright © 2015 Cask Data, Inc.
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

package co.cask.cdap.etl.batch.mapreduce;

import co.cask.cdap.api.data.DatasetInstantiationException;
import co.cask.cdap.api.dataset.Dataset;
import co.cask.cdap.api.mapreduce.MapReduceTaskContext;
import co.cask.cdap.api.metrics.Metrics;
import co.cask.cdap.etl.api.LookupProvider;
import co.cask.cdap.etl.api.batch.BatchRuntimeContext;
import co.cask.cdap.etl.common.AbstractTransformContext;

import java.util.Map;

/**
 * Batch runtime context that delegates most operations to MapReduceTaskContext. It also extends
 * {@link AbstractTransformContext} in order to provide plugin isolation between pipeline plugins. This means sources,
 * transforms, and sinks don't need to worry that plugins they use conflict with plugins other sources, transforms,
 * or sinks use.
 */
public class MapReduceRuntimeContext extends AbstractTransformContext implements BatchRuntimeContext {
  private final MapReduceTaskContext context;

  public MapReduceRuntimeContext(MapReduceTaskContext context, Metrics metrics, LookupProvider lookup, String stageId) {
    super(context, metrics, lookup, stageId);
    this.context = context;
  }

  @Override
  public long getLogicalStartTime() {
    return context.getLogicalStartTime();
  }

  @Override
  public Map<String, String> getRuntimeArguments() {
    return context.getRuntimeArguments();
  }

  @Override
  public <T> T getHadoopJob() {
    throw new UnsupportedOperationException("Not supported");
  }

  @Override
  public <T extends Dataset> T getDataset(String name) throws DatasetInstantiationException {
    return context.getDataset(name);
  }

  @Override
  public <T extends Dataset> T getDataset(String name,
                                          Map<String, String> arguments) throws DatasetInstantiationException {
    return context.getDataset(name, arguments);
  }
}
