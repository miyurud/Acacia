/**
Copyright 2015 Acacia Team

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

#include <iostream>
#include <fstream>
#include <boost/filesystem.hpp>
#include <boost/lexical_cast.hpp>
#include <boost/algorithm/string.hpp>

using namespace std;
using namespace boost::filesystem;

int main(int argc, char* argv[]) {
//	if(exists("/tmp/wcout2")){
//		remove("/tmp/wcout2");
//	}
//
//	int i = system("hadoop fs -get /user/miyuru/wcout/part-r-00000 /tmp/wcout2");
//
//	if(i != 0){
//		cout << "Error in downloading the file from HDFS..." << endl;
//	}

	string line;
	ifstream infile("/tmp/wcout2");
	vector<string> strs;
	char* lln = new char[1024];
	strcat(lln, argv[1]);
	strcat(lln, "/bin/hadoop fs -put /tmp/notinverts /user/miyuru/notinverts/notinverts");

	cout << lln << endl;

	int i = 0;
	long prevval = -1;
	long curval = -1;
	bool flag = false;

	if(infile.is_open()){
		ofstream outfile;
		outfile.open("/tmp/notinverts");

		while(infile.good()){
			getline(infile, line);

			boost::split(strs, line, boost::is_any_of("\t"));
			try{
				curval = boost::lexical_cast<long long>(strs[0]);
			}catch(boost::bad_lexical_cast &){
				//out << "Error : |" << strs[0] << "|" << endl;
				//Just ignore this
				continue;
			}

			if(prevval == -1){
				prevval = curval;
			}else{
				prevval += 1;

				while(prevval < curval){
					//cout << "Not in : " << prevval << endl;
					outfile << prevval << "\t-1" << endl;
					prevval += 1;
					flag = true;
				}
				prevval = curval;
			}
		}
		infile.close();
		outfile.close();
	}

	//Flag true meeans we found some not in verts.
	if(flag){
		cout << "Command is : " << lln << endl;
		i = system(lln);

		if(i != 0){
			cout << "Error in uploading the file to HDFS. Error code : " << i << endl;
		}

//		i = system("hadoop fs -put /tmp/notinverts /user/miyuru/notinverts");
//
//		if(i != 0){
//			cout << "Error in uploading the file to HDFS. Error code : " << i << endl;
//		}
	}

	return 0;
}
