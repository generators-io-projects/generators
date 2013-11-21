io.generators
==========

io.generators is a small framework for generating random/sequential primitives and types in Java. 
It is especially usefull for testing.

Releases
-------------
First snapshot was published: 

    io.generators:generators-core:0.1-SNAPSHOT

The snapshot is hosted at Sonatype Nexus Snapshot repository so in order to get it add following reppository to your pom:

         <repositories>
             <repository>
                 <id>sonatype-nexus-snapshots</id>
                 <name>Sonatype Nexus Snapshots</name>
                 <url>https://oss.sonatype.org/content/repositories/snapshots</url>
                 <releases>
                     <enabled>false</enabled>
                 </releases>
                 <snapshots>
                     <enabled>true</enabled>
                 </snapshots>
             </repository>
         </repositories>
