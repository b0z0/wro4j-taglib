/*
* Copyright 2011, 2012 France Télécom
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.orange.wro.taglib.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro.isdc.wro.model.resource.ResourceType;

/**
 * Keeps the minimized and unminimized files for each resource type.
 * 
 * @author Julien Wajsberg
 */
public class FilesGroup {
	private String name;
	private Map<ResourceType,List<String>> group = new HashMap<ResourceType, List<String>>();
	private Map<ResourceType,String> minimizedFiles = new HashMap<ResourceType, String>();
	
	public FilesGroup(String name) {
		this.name = name;
	}
	
	public void put(ResourceType type, List<String> files) {
		group.put(type, files);
	}
	
	public List<String> get(ResourceType type) {
		return group.get(type);
	}
	
	public void putMinimizedFile(ResourceType type, String minimizedFile) {
		minimizedFiles.put(type, minimizedFile);
	}
	
	public String getMinimizedFile(ResourceType type) {
		return minimizedFiles.get(type);
	}
	
	public String getName() {
		return name;
	}
	
}
