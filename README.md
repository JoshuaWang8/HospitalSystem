# Hospital System

## Project Overview
This is an Algorithms and Data Structures project and is meant to simulate hospital systems. There are three main components:
- Booking Systems for three different hospitals
- Login System
- Symptom Tree

### File Structure
- `src\`: Contains implementations of the components mentioned above.
- `test\`: Contains tests used for testing functionality of components in this project.

## Booking Systems
There are three booking systems using different implementations based on the hospitals' requirements. There are two methods for each system:
- `iterator`: Iterates through patient appointments in the system in the correct order
- `addPatient`: Adds a new patient to the system

These have been implemented in the files `src/Hospital1.java`, `src/Hospital2.java` and `src/Hospital3.java`.

All three hospitals work from 08:00 to 18:00, with a lunch break from 12:00 to 13:00. Additionally, each hospital has different time complexity requirements for each function.

### Hospital 1
For hospital 1, appointments are fixed and last exactly 20 minutes (e.g. 08:00, 08:20, 08:40 are valid time slots, while 08:01, 08:22, 08:41 are not), and can be occupied by at most one patient. If a requested time is invalid or already occupied, the patient is not added to the system. Both `addPatient` and `iterator` should run as quickly as possible

To implement this, a hash map was used for storing patients, where hashes were calculated by *(hour - 8) + x*, where *x* is either 0 if the minutes was 00, 1 if the minutes was 20 and 2 if the minutes was 40. This gives *O(1)* patient insertion and *O(1)* search.  *O(1)* space complexity is also achieved, as the number of time slots each day is equal to the size of the hash map.

Since hashes are calculated by patient times when inserting into the hash map, patients will already be ordered and thus `iterator` will run in *O(1)* time as the hash map will always have 27 possible time slots each day.

### Hospital 2
Hospital 2 requires `addPatient` in *O(n)* time and `iterator` as quickly as possible.

The data structure used for storing patients is an ordered linked list. This satisfies the constraint of `addPatient` being in *O(n)* time, as the number of nodes traversed when finding the position to insert into will be proportional to the number of nodes in the list. Space complexity for this data structure is also *O(n)*.

`iterator` for Hospital 2 will run in *O(n)* time, since patients are sorted upon insertion and iterator will only iterate through the entire linked list of patients.

### Hospital 3
Hospital 3 requires `addPatient` to be in *O(1)* time and `iterator` to be as quick as possible.

Patients are stored in a queue implemented with a linked list, where `addPatient` inserts patients at the tail of the linked list in *O(1)* time. This maintains the priority of patients' appointments if two patients book the same time, while satisfying the *O(1)* insertion constraint.

`iterator` for Hospital 3 performs radix sort (using bucket sort as the stable sorting algorithm) to sort the stored patients by time, before iterating through all values. This gives an average case of *O(n)*, where radix sort runs in *O(n)* and iterating through all values takes *O(n)*. However, in the event where all patients were to book the same time slot, then worst-case time becomes *O($n^2$)*, though this is an unlikely scenario.


## Login System
This system stores a secure collection of users who have signed up to the hospital appointment system, and is implemented in `src/LoginSystem.java`. Users can sign up by providing their email and password, and can also change their password or remove themselves from the system.

The system is implemented in the form of a hash table, where it is initialised to a static size of 101, and when the number of users exceeds the current size, the hash table size is tripled.

The hash function is composed of a hash code and compression function:
- Hash Code: For a string with letters *$s_1, s_2, s_3, ..., s_n$*, and *$A_n$* representing the ASCII value for the letter *$s_n$* and *$c=31$*, the hash code is: $$[(((A_1\times c+A_2)\times c+A_3)\times c + A_4)\times c + ...]+A_n$$
- Compression Function: (hash code) $mod$ (hash table size)

Collisions in hashes are handled by using linear probing.


## Tree of Symptoms
The tree of symptoms is a tree that can be used by doctors for diagnosing patients. Each node in the tree represents a symptom and has a corresponding severity level. For all nodes, the left sub-tree contains more mild symptoms (with lower severity levels) and the right sub-tree contains more severe symptoms. It is assumed that there is at least one symptom in the tree and that each symptom has a unique severity level.

The tree can be restructured so that doctors can begin diagnosis from a specified severity threshold. When restructuring, the root symptom of the new tree will be the smallest severity that satisfies *severity $>=$ threshold*. If no such symptom exists, the most severe symptom is used as the new root node.