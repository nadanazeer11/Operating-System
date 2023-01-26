//package Cpu;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//import memoryManager.MemoryManager;
//import memoryManager.ProcessObject;
//
//public class Executer {
//	// ArrayList<String> lineList;
//	MemoryManager memoryManager;
//	ProcessObject processObjects;
//	// ProcessObject processObject2;
//	private boolean fileIsBlocked = true;
//	private boolean inputIsBlocked = true;
//	private boolean outputIsBlocked = true;
//
//	// 3ayzeen execute teraga3 boolean b hya khalst wala la law makhlstsh yb2a ashan
//	// blocked yb2a lazm nshoof meen blocked
//	// w ne3mel pc=pc-1 ashan nerga3 n execute el line dah tany ama yb2a mesh
//	// blocked
//	// w nkhaly el os tnady 3ala nfsha ashan tkml
//	protected SystemResponse executeSystemCall(SystemCall systemCall) {
//		if (systemCall.lineList.get(0).equals("print")) {
//			return print(systemCall.lineList.get(1), systemCall);
//		} else {
//			if (systemCall.lineList.get(0).equals("assign")) {
//				return assign(systemCall.lineList.get(1), systemCall.lineList.get(2), systemCall);
//			} else {
//				if (systemCall.lineList.get(0).equals("writeFile")) {
//					return writeFile(systemCall);
//				} else {
//					if (systemCall.lineList.get(0).equals("readFile")) {
//						SystemResponse response = new SystemResponse();
//						return response;
//					} else {
//						if (systemCall.lineList.get(0).equals("printFromTo")) {
//							return printFromTo(systemCall);
//						} else {
//							if (systemCall.lineList.get(0).equals("semWait")) {
//								return semWait(systemCall);
//							} else {
//								if (systemCall.lineList.get(0).equals("semSignal")) {
//									return semSignal(systemCall);
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//		SystemResponse response = new SystemResponse();
//		return response;
//	}
//
//	private SystemResponse semWait(SystemCall systemCall) {
//		SystemResponse response = new SystemResponse();
//		String resourceName = systemCall.lineList.get(1);
//		if (resourceName.equals("userInput")) {
//			if (tryToTakeInMutex(systemCall.inMutex, systemCall.processId)) {
//				response.isExecuted = true;
//				Mutex mutex = systemCall.inMutex;
//				response.setMutex(systemCall.inMutex);
//				System.out.println("Process Num " + systemCall.processId + " Successfully took input Mutex");
//				response.setNumOfExecutedLines(1);
//				return response;
//			} else {
//				response.isExecuted = false;
//				Mutex mutex = systemCall.inMutex;
//				response.setMutex(systemCall.inMutex);
//				response.setNumOfExecutedLines(1);
//				System.out.println("Process Num " + systemCall.processId + " Can't take input Mutex");
//				return response;
//			}
//		} else {
//			if (resourceName.equals("userOutput")) {
//				if (tryToTakeOutMutex(systemCall.outMutex, systemCall.processId)) {
//					response.isExecuted = true;
//					Mutex mutex = systemCall.outMutex;
//					response.setMutex(systemCall.outMutex);
//					System.out.println("Process Num " + systemCall.processId + " Successfully took output Mutex");
//					response.setNumOfExecutedLines(1);
//					return response;
//				} else {
//					response.isExecuted = false;
//					Mutex mutex = systemCall.outMutex;
//					response.setMutex(systemCall.outMutex);
//					response.setNumOfExecutedLines(1);
//					System.out.println("Process Num " + systemCall.processId + " Can't take output Mutex");
//					return response;
//				}
//			} else {
//				if (tryToTakeFileMutex(systemCall.fileMutex, systemCall.processId)) {
//					response.isExecuted = true;
//					Mutex mutex = systemCall.fileMutex;
//					response.setMutex(systemCall.fileMutex);
//					System.out.println("Process Num " + systemCall.processId + " Successfully took file Mutex");
//					response.setNumOfExecutedLines(1);
//					return response;
//				} else {
//					response.isExecuted = false;
//					Mutex mutex = systemCall.fileMutex;
//					response.setMutex(systemCall.fileMutex);
//					response.setNumOfExecutedLines(1);
//					System.out.println("Process Num " + systemCall.processId + " Can't take file Mutex");
//					return response;
//				}
//			}
//
//		}
//	}
//
//	private SystemResponse semSignal(SystemCall systemCall) {
//		String resourceName = systemCall.lineList.get(1);
//		SystemResponse response = new SystemResponse();
//		response.isSemSignal = true;
//		response.isExecuted = true;
//		response.setNumOfExecutedLines(1);
//		if (resourceName.equals("userInput")) {
//			if (leaveInMutex(systemCall.inMutex)) {
//				Mutex mutex = systemCall.inMutex;
//				response.setMutex(systemCall.inMutex);
//			}
//		} else {
//			if (resourceName.equals("userOutput")) {
//				if (leaveOutMutex(systemCall.outMutex)) {
//					Mutex mutex = systemCall.outMutex;
//					response.setMutex(systemCall.outMutex);
//				}
//			} else {
//				if (leaveFileMutex(systemCall.fileMutex)) { // nada
//					Mutex mutex = systemCall.fileMutex;
//					response.setMutex(systemCall.fileMutex);
//				}
//			}
//		}
//		return response;
//	}
//
//	protected Executer(MemoryManager memoryManager) {
//		super();
//		this.memoryManager = memoryManager;
//	}
//
//	protected SystemResponse print(String line, SystemCall systemCall) {
//		SystemResponse response = new SystemResponse();
//		int pid = systemCall.processId;
//		ProcessObject processObject = memoryManager.getProccessObjectFromProcessBlock(pid, line);
//		if (processObject == null) {
//			System.out.println("cant get processobject id and name =" + pid + line);
//		}
//		System.out.println(processObject.getProcessData());
//		response.isExecuted = true;
//		Mutex mutex = new Mutex("fake");
//		response.setMutex(mutex);
//		response.setNumOfExecutedLines(1);
//		return response;
//	}
//
//	protected void writeListIntoFile(String filePath, ArrayList<String> arr) throws IOException {
//		FileWriter fw = new FileWriter(filePath, true);
//		for (String str : arr) {
//			fw.write(str + System.lineSeparator());
//		}
//		fw.close();
//	}
//
//	void printList(ArrayList<String> list) {
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i));
//		}
//	}
//
//	protected SystemResponse writeFile(SystemCall systemCall) {
//		String fileName = systemCall.lineList.get(1); // a el gowaha program1.tct
//		String data = systemCall.lineList.get(2); // b el gowaha 5
//		int pid = systemCall.processId;
//		ArrayList<String> oldFile = new ArrayList<String>();
//		// file created gowah el b el b 5
//		ProcessObject objectb = memoryManager.getProccessObjectFromProcessBlock(pid, data);
//		ProcessObject objecta = memoryManager.getProccessObjectFromProcessBlock(pid, fileName);
//		oldFile.add("khara");
//		
//		if (!objectb.getExtraData2().equals("No")) {
//			String word = objectb.getExtraData2();
//			System.out.println("What is written in writeFile" + word);
//			oldFile.add(word);
//		} else {
//			String word = "" + objectb.getProcessData();
//			oldFile.add(word);
//		}
//
//		SystemResponse response = new SystemResponse();
//		try {
//			writeListIntoFile(objecta.getExtraData(), oldFile);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		response.isExecuted = true;
//		Mutex mutex = new Mutex("fake");
//		response.setMutex(mutex);
//		response.setNumOfExecutedLines(1);
//
//		return response;
//
//	}
//
//	protected SystemResponse printFromTo(SystemCall systemCall) {
//		String x = systemCall.lineList.get(1);
//		String y = systemCall.lineList.get(2);
//		ProcessObject processObjectOne = this.memoryManager.getProccessObjectFromProcessBlock(systemCall.processId, x);
//		ProcessObject processObjectTwo = this.memoryManager.getProccessObjectFromProcessBlock(systemCall.processId, y);
//
//		SystemResponse response = new SystemResponse();
//		response.isExecuted = true;
//		response.setNumOfExecutedLines(1);
//		Mutex mutex = new Mutex("fake");
//		response.setMutex(mutex);
//
//		memoryManager.printObjectsInProcessBlock(systemCall.processId);
//		if (processObjectOne == null) {
//			System.out.println("cant get processobject id and name =" + systemCall.processId + x);
//		} else {
//			if (processObjectTwo == null) {
//				System.out.println("cant get processobject id and name =" + systemCall.processId + x);
//			} else {
//				int var1 = processObjectOne.getProcessData();
//				int var2 = processObjectTwo.getProcessData();
//				int min = Math.min(var1, var2);
//				int max = Math.max(var2, var1);
//
//				for (int i = min; i <= max; i++) {
//					if (min == max) {
//						System.out.println("There exists no numbers between " + min + " and " + max);
//						break;
//					}
//					System.out.println(i);
//				}
//			}
//		}
//		return response;
//	}
//
//	protected SystemResponse assign(String fileName, String y, SystemCall systemCall) {
//
//		processObjects = memoryManager.getProccessObjectFromProcessBlock(systemCall.processId, fileName);
//
//		if (processObjects == null) {
//			System.out.println("Object name " + fileName + " was not found. Creating a new object ");
//			processObjects = new ProcessObject("safra", 99999999);
//		}
//		return assignSearch(processObjects, y, systemCall);
//	}
//
//	protected SystemResponse assignSearch(ProcessObject object, int y, SystemCall systemCall) {
//		String name = systemCall.lineList.get(1);
//		object.setProcessData(y);
//		object.setName(name);
//		this.memoryManager.addProccessObjectToProcessBlock(systemCall.processId, object);
//		SystemResponse response = new SystemResponse();
//		response.isExecuted = true;
//		Mutex mutex = new Mutex("fake");
//		response.setMutex(mutex);
//		response.setNumOfExecutedLines(1);
//		return response;
//	}
//
//	protected SystemResponse assignSearch(ProcessObject object, String y, SystemCall systemCall) {
//		if (y.equals("input")) {
//			// take input then set value of object
//
//			try {
//				String name = systemCall.lineList.get(1);
//
//				Scanner sc = new Scanner(System.in);
//				System.out.println("Please Enter a value for object/File Name : " + y);
//
//				String userInput = sc.nextLine();
//
//				if (userInput.matches(".*\\d.*")) {
//					System.out.println(" Contains digit only");
//
//					int x = Integer.parseInt(userInput);
//					object.setProcessData(x);
//					object.setName(name);
//
//				} else {
//					System.out.println(" Only String/words found");
//					if (userInput.endsWith("txt")) {
//						object.setExtraData(userInput);
//						object.setName(name);
//					}
//
//					else {
//						object.setExtraData2(userInput);
//						object.setName(name);
//					}
//
//				}
//
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			this.memoryManager.addProccessObjectToProcessBlock(systemCall.processId, object);
//
//			SystemResponse response = new SystemResponse();
//			response.isExecuted = true;
//			Mutex mutex = new Mutex("fake");
//			response.setMutex(mutex);
//			response.setNumOfExecutedLines(0);
//
//			return response;
//
//		} else {
//			if (y.equals("readFile")) {
//				// 4th word
//				String word = systemCall.lineList.get(3);
//
//				ProcessObject processObject = memoryManager.getProccessObjectFromProcessBlock(systemCall.processId,
//						word);
//				int value = processObject.getProcessData();
//				System.out.println("hi");
//				assignSearch(processObject, value, systemCall);
//
//				// int x =readFile(word,processObject);
//				// take the answer of read file and set the object back
//				// this.memoryManager.setProccessObjectFromProcessBlock(systemCall.processId, ,
//				// object);
//
//			}
//			/*
//			 * else {
//			 * 
//			 * }
//			 */
//			SystemResponse response = new SystemResponse();
//			response.isExecuted = true;
//			Mutex mutex = new Mutex("fake");
//			response.setMutex(mutex);
//			response.setNumOfExecutedLines(0);
//			return response;
//		}
//
//	}
//
//	public boolean tryToTakeInMutex(Mutex mutex, int pid) {
//		// here we assume u only call it when current running process is the current
//		// running process
//		if (mutex.getValue() == 1) {
//			mutex.setOwnerId(pid);
//			mutex.setValue(0);
//			inputIsBlocked = false;
//
//			return true;
//			// if you took the mutex you will finish this method and continue your normal
//			// execution
//		} else {
//			inputIsBlocked = true;
//			return false;
//			// the mutex is already taken
//			// currentRunningProcess.setState(State.Blocked);
//			// this.dispatcher.enqueueBlockedInput(currentRunningProcess);
//			// you should call the program to procceed with the next process
//			// this.runOs();
//		}
//	}
//
//	public boolean leaveOutMutex(Mutex mutex) {
//		mutex.setValue(1);
//		return true;
//	}
//
//	public boolean leaveInMutex(Mutex mutex) {
//		mutex.setValue(1);
//		return true;
//	}
//
//	public boolean leaveFileMutex(Mutex mutex) {
//		mutex.setValue(1);
//		return true;
//	}
//
//	public boolean tryToTakeOutMutex(Mutex mutex, int pid) {
//		if (mutex.getValue() == 1) {
//			mutex.setOwnerId(pid);
//			mutex.setValue(0);
//			outputIsBlocked = false; // el moh alo l nada
//			return true;
//		} else {
//			outputIsBlocked = true;
//			return false;
//		}
//	}
//
//	public boolean tryToTakeFileMutex(Mutex mutex, int pid) {
//		if (mutex.getValue() == 1) {
//
//			mutex.setOwnerId(pid);
//			mutex.setValue(0);
//			fileIsBlocked = false;
//			return true;
//		} else {
//			fileIsBlocked = true;
//			return false;
//		}
//	}
//
//}
