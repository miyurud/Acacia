# Copyright 2015 Acacia Team
# 
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#     http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

C = gcc
CC = g++
SOURCE = cpp/src
OUTDIR = bin
THIRD_PARTY_INCLUDES=/nfs/data0/miyuru/software/boost_1_48_0/include
THIRD_PARTY_LIBS=/nfs/data0/miyuru/software/boost_1_48_0/lib


all:
	$(CC) $(SOURCE)/NotInVertsSearch.cpp -o $(OUTDIR)/NotInVertsSearch -I $(THIRD_PARTY_INCLUDES) -L $(THIRD_PARTY_LIBS) -lboost_system -lboost_filesystem

clean:
	rm -rf *.o
