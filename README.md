# HospitalTriageSystem

This is an implementation of a system to triage patients in a hospital emergency ward. 
Patients are triaged according to both medical priority and wait time. Priorities are positive integers; the highest priority is 1. Normally patients are seen in priority order. However, if there are patients who have waited longer than a specified time (maxWait), they are seen first, in order of their arrival.

Adding a patient to the system when they arrive, and also removing the next patient to be seen from the system when a physician becomes available are done in linear time.

Implementation:- To achieve this, the system uses two location-aware priority queues implemented with min heaps: one based on medical priority and the other on arrival time. The heaps use ArrayLists to store the underlying binary trees.
