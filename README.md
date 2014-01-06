io.generators
==========

io.generators is a small framework for generating random/sequential primitives and types in Java. 
It is especially useful for testing.

Releases
-------------
First release (0.1) is planned for the middle of the January 2014. Interfaces/packages/library dependencies are not stable yet and may change. Therefore major release version is 0.x not 1.x

Snapshots are published/updated quite frequently: 

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
