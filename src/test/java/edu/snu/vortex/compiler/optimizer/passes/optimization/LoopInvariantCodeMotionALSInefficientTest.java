/*
 * Copyright (C) 2017 Seoul National University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.snu.vortex.compiler.optimizer.passes.optimization;

import edu.snu.vortex.client.JobLauncher;
import edu.snu.vortex.common.dag.DAG;
import edu.snu.vortex.compiler.TestUtil;
import edu.snu.vortex.compiler.ir.IREdge;
import edu.snu.vortex.compiler.ir.IRVertex;
import edu.snu.vortex.compiler.optimizer.passes.LoopGroupingPass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

/**
 * Test {@link LoopOptimizations.LoopInvariantCodeMotionPass} with ALS Inefficient workload.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(JobLauncher.class)
public class LoopInvariantCodeMotionALSInefficientTest {
  private DAG<IRVertex, IREdge> inefficientALSDAG;
  private DAG<IRVertex, IREdge> groupedDAG;

  @Before
  public void setUp() throws Exception {
    inefficientALSDAG = TestUtil.compileALSInefficientDAG();
    groupedDAG = new LoopGroupingPass().process(inefficientALSDAG);
  }

  @Test
  public void testForInefficientALSDAG() throws Exception {
    final long expectedNumOfVertices = groupedDAG.getVertices().size() + 3;

    final DAG<IRVertex, IREdge> processedDAG = LoopOptimizations.getLoopInvariantCodeMotionPass()
        .process(groupedDAG);
    assertEquals(expectedNumOfVertices, processedDAG.getVertices().size());
  }

}