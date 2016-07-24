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

package org.acacia.server;

/**
 * Class GraphStatus
 */
public class GraphStatus {
	//Note that these values must be present on the database on the GRAPH_STATUS table as well.
	public static val LOADING:Int = 1n;
	public static val OPERATIONAL:Int = 2n;
	public static val DELETED:Int = 3n;
    public static val STREAMING:Int = 4n;
}