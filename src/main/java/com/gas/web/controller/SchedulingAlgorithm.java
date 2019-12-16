/**
 * Copyright 2012-2013 University Of Southern California
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
package com.gas.web.controller;

import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.core.CloudSim;
import org.workflowsim.*;
import org.workflowsim.examples.scheduling.DataAwareSchedulingAlgorithmExample;
import org.workflowsim.utils.ClusteringParameters;
import org.workflowsim.utils.OverheadParameters;
import org.workflowsim.utils.Parameters;
import org.workflowsim.utils.ReplicaCatalog;

import java.io.File;
import java.util.Calendar;
import java.util.List;

/**
 * This FCFS Scheduling Algorithm
 *
 * @author Weiwei Chen
 * @since WorkflowSim Toolkit 1.1
 * @date Nov 9, 2013
 */
public class SchedulingAlgorithm extends DataAwareSchedulingAlgorithmExample {

    public List<CondorVM> CondorVMList;
    public List<Job> taskList;

    public List<CondorVM> getCondorVMList() {
        return CondorVMList;
    }

    public void setCondorVMList(List<CondorVM> condorVMList) {
        CondorVMList = condorVMList;
    }

    public List<Job> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Job> taskList) {
        this.taskList = taskList;
    }

    ////////////////////////// STATIC METHODS ///////////////////////
    /**
     * Creates main() to run this example This example has only one datacenter
     * and one storage
     */
    public void process(String path, int vmnum, int algorithm) {

        try {
            // First step: Initialize the WorkflowSim package. 
            /**
             * However, the exact number of vms may not necessarily be vmNum If
             * the data center or the host doesn't have sufficient resources the
             * exact vmNum would be smaller than that. Take care.
             */
            int vmNum = vmnum;//number of vms;
            /**
             * Should change this based on real physical path
             */
            //String daxPath = "config/dax/leadmm.xml";
            String daxPath = path;


            File daxFile = new File(daxPath);
            if (!daxFile.exists()) {
                Log.printLine("Warning: Please replace daxPath with the physical path in your working environment!");
                return;
            }

            /**
             * Since we are using HEFT planning algorithm, the scheduling
             * algorithm should be static such that the scheduler would not
             * override the result of the planner
             *                                     <option value="0">FCFS</option>
             *                                     <option value="1">MAXMIN</option>
             *                                     <option value="2">MINMIN</option>
             *                                     <option value="3">ROUNDROBIN</option>
             *                                     <option value="4">MCT</option>
             */
            Parameters.SchedulingAlgorithm sch_method  = Parameters.SchedulingAlgorithm.FCFS;;
            switch (algorithm){
                case 0:
                    sch_method = Parameters.SchedulingAlgorithm.FCFS;
                    break;
                case 1:
                    sch_method = Parameters.SchedulingAlgorithm.MAXMIN;
                    break;
                case 2:
                    sch_method = Parameters.SchedulingAlgorithm.MINMIN;
                    break;
                case 3:
                    sch_method = Parameters.SchedulingAlgorithm.ROUNDROBIN;
                    break;
                case 4:
                    sch_method = Parameters.SchedulingAlgorithm.MCT;
                    break;
            }

            Parameters.PlanningAlgorithm pln_method = Parameters.PlanningAlgorithm.INVALID;
            ReplicaCatalog.FileSystem file_system = ReplicaCatalog.FileSystem.LOCAL;

            /**
             * No overheads
             */
            OverheadParameters op = new OverheadParameters(0, null, null, null, null, 0);

            /**
             * No Clustering
             */
            ClusteringParameters.ClusteringMethod method = ClusteringParameters.ClusteringMethod.NONE;
            ClusteringParameters cp = new ClusteringParameters(0, 0, method, null);

            /**
             * Initialize static parameters
             */
            Parameters.init(vmNum, daxPath, null,
                    null, op, cp, sch_method, pln_method,
                    null, 0);
            ReplicaCatalog.init(file_system);

            // before creating any entities.
            int num_user = 1;   // number of grid users
            Calendar calendar = Calendar.getInstance();
            boolean trace_flag = false;  // mean trace events

            // Initialize the CloudSim library
            CloudSim.init(num_user, calendar, trace_flag);

            WorkflowDatacenter datacenter0 = createDatacenter("Datacenter_0");

            /**
             * Create a WorkflowPlanner with one schedulers.
             */
            WorkflowPlanner wfPlanner = new WorkflowPlanner("planner_0", 1);
            /**
             * Create a WorkflowEngine.
             */
            WorkflowEngine wfEngine = wfPlanner.getWorkflowEngine();
            /**
             * Create a list of VMs.The userId of a vm is basically the id of
             * the scheduler that controls this vm.
             */
            List<CondorVM> vmlist0 = createVM(wfEngine.getSchedulerId(0), Parameters.getVmNum());

            /**
             * Submits this list of vms to this WorkflowEngine.
             */
            wfEngine.submitVmList(vmlist0, 0);

            /**
             * Binds the data centers with the scheduler.
             */
            wfEngine.bindSchedulerDatacenter(datacenter0.getId(), 0);

            CloudSim.startSimulation();
            List<Job> outputList0 = wfEngine.getJobsReceivedList();
            CloudSim.stopSimulation();
            CondorVMList = vmlist0;
            taskList = outputList0;
            printJobList(outputList0);
        } catch (Exception e) {
            Log.printLine("The simulation has been terminated due to an unexpected error");
        }
    }
}