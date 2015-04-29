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

#This is the scipt for compiling testcases.
x10c -classpath build/classes src/org/acacia/frontend/AcaciaFrontEndProtocol.x10 src/org/acacia/frontend/AcaciaFrontEnd.x10 src/org/acacia/frontend/AcaciaFrontEndServiceSession.x10 src/org/acacia/util/Utils.x10 src/org/acacia/util/Conts.x10 src/org/acacia/server/AcaciaServer.x10 src/org/acacia/partitioner/hadoop/HadoopOrchestrator.x10 src/org/acacia/partitioner/hbase/HBaseInterface.x10 src/org/acacia/partitioner/hadoop/HDFSInterface.x10 src/org/acacia/partitioner/MetisInterface.x10 src/org/acacia/log/Logger.x10 src/org/acacia/partitioner/hadoop/HDFSFile.x10 src/org/acacia/metadata/db/HSQLDBInterface.x10 src/org/acacia/server/GraphStatus.x10 src/org/acacia/vertexcounter/VertexCounter.x10 src/org/acacia/vertexcounter/VertexCounterServiceSession.x10 src/org/acacia/vertexcounter/VertexSingleton.x10 src/test/acacia/vertexcounter/TestAcaciaVertexCounter.x10 src/test/acacia/vertexcounter/TestAcaciaVertexCounterClient.x10 -d build/classes
