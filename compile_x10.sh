###
# Copyright 2015 Acacia Team
# 
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
##

APPDIR=/home/miyurud/software/x10dt/workspace/Acacia

x10c -classpath build/classes:lib/blueprints-core-2.4.0.jar:lib-gremlin-groovy/gremlin-groovy-2.4.0.jar:lib/hadoop-core-1.0.4.jar:lib/commons-io-1.4.jar $APPDIR/src/org/acacia/frontend/AcaciaFrontEndProtocol.x10 $APPDIR/src/org/acacia/frontend/AcaciaFrontEnd.x10 $APPDIR/src/org/acacia/frontend/AcaciaFrontEndServiceSession.x10 $APPDIR/src/org/acacia/util/Utils.x10 $APPDIR/src/org/acacia/util/Conts.x10 $APPDIR/src/org/acacia/server/AcaciaServer.x10 $APPDIR/src/org/acacia/partitioner/hadoop/HadoopOrchestrator.x10 $APPDIR/src/org/acacia/partitioner/hadoop/HDFSInterface.x10 $APPDIR/src/org/acacia/partitioner/MetisInterface.x10 $APPDIR/src/org/acacia/log/Logger.x10 $APPDIR/src/org/acacia/partitioner/hadoop/HDFSFile.x10 $APPDIR/src/org/acacia/metadata/db/HSQLDBInterface.x10 $APPDIR/src/org/acacia/server/GraphStatus.x10 $APPDIR/src/org/acacia/tinkerpop/blueprints/impl/AcaciaGraph.x10 $APPDIR/src/org/acacia/frontend/AcaciaGremlinInterpreter.x10 $APPDIR/src/test/acacia/server/x10/TestAcaciaInstance.x10 $APPDIR/src/org/acacia/util/PlaceToNodeMapper.x10 -d $APPDIR/build/classes
