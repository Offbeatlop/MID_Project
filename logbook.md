# Logbook
## First meeting

In order to desing our application, we have decided to use Java as programming language, and GitHub and Trello
to share our thoughts. 

Trying to decide how to implement our project, we first came up with the idea of having a master node, where to
upload our files and share what we wanted. It would be the manager among the computers, taking each user's files
and merging them in a common filesystem, then replicating it to every user's local machine.

After discussing it with the professor, we changed this vision for a decentralized system, where peers directly
interchange the files.

## Second meeting

We had agreed that all machines would act as peers. This carries the
problem of synchronizing all files at the same time without a central
node storing the "real" version of the filesystem.

We have agreed on the following file synchronization process:

1. Each node will inspect its own shared filesystem and return a
   description of it in JSON format
2. This JSON will be sent to all the other peers, and JSONs from them
   will be received.
3. Each node knowing what its peers have in their filesystems, they
   will start the operations in order to have the same version of the
   filesystem: Request the sending of files they don't have, erase
   those that other machines do not have, etc.

To achieve this, we add the following tasks to the TODO list:

- Implement a program that given a filesystem, returns its
  description in JSON format.
- Start studying about how to connect several machines over the
  network with the RPC/RMI middleware.


## Notes
network side, file system
run the application to discover who our neigbour machines would be on the network (peers).
then you can 1) have events from your file system (your own machine) and 2) communicate with the other computers
(events you receive from the network) here's a new file, take the path... and you copy it to your computer
application which listening to the network and the file system.
if you change in your own machine, everyone has a copy and it gets modified in all the computers.
coordinate the machines in order to not send the same file several times to a computer.
