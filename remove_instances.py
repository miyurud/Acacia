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

import commands

def getHostList():
    hostLst = []
    for line in open('./machines.txt'):
	if line[0:1] != '#':
		hostLst.append(line.strip())

    return hostLst

def removeWorkers():
    hostList = getHostList()
    for host in hostList:

        result = commands.getoutput('ssh ' + host + ' netstat -anp | grep :7778')

        index1 = result.find('/java')
	
	resultstr = result[:index1]
	index2 = resultstr.rfind(' ')
	psid = resultstr[index2:]

	print 'host : ' + host 
	print 'ps id : ' + psid

	if len(psid) > 0:
		result = commands.getoutput('ssh ' + host + ' kill ' + psid)	
		print 'result :|' + str(result) + '|'
    print 'Completed removing Acacia Instances...'
removeWorkers()
