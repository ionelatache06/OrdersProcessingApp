package com.ionela.sacomdemo.service;

import com.ionela.sacomdemo.parser.ReadXMLFile;

import java.io.File;
import java.nio.file.*;
import java.util.*;

public class FileWatchService {

	public static void watch(){
		//Deque<Path> wPathsToFiles = new ArrayDeque<>();
		//We create a new watch service
		try (WatchService service = FileSystems.getDefault().newWatchService()){
			//each path of a directory will have its own watchkey
			Map<WatchKey, Path> keyMap = new HashMap<>();
			Path path = Paths.get("../SacomDemo/src/main/resources/input");
			//We watch for events of creation type
			keyMap.put(path.register(service,
					StandardWatchEventKinds.ENTRY_CREATE
					),path);
			WatchKey watchKey;

			do{
				//Return a queued key
				watchKey = service.take();
				Path eventDir = keyMap.get(watchKey);

				//process the pending events for the specified key
					for (WatchEvent<?> event: watchKey.pollEvents()){
						//Test if we are getting the events we signed up for
						WatchEvent.Kind<?> kind = event.kind();
						//Retrieve the path which is stored as the context of the event
						Path eventPath = (Path) event.context();
						System.out.println(eventDir + ": " + kind + ": " + eventPath);
						Path pathToFile = eventDir.resolve(eventPath);
						//Transform form path to file
						File pathFile = pathToFile.toFile();
						//String absolutePath = pathToFile.getAbsolutePath();
						//wPathsToFiles.addLast(pathToFile);
						System.out.println(pathToFile);
						//Read our file
						ReadXMLFile.process(pathFile);
					}
			//Reset watchKey
			}while(watchKey.reset());

		}catch(Exception e){
			e.printStackTrace();
		}
		//return wPathsToFiles;
	}
}