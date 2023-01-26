package memoryManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import processManager.Pcb;
import processManager.State;

import java.util.*;

public class Disk {

	public Disk() {
		ArrayList<String> diskList = new ArrayList<String>();
		diskList.add("****");
		try {
			writeListIntoFile("disk.txt", diskList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void moveToDisk(int pid, ArrayList<ProcessObject> objectsList, ArrayList<String> lineList,
			ArrayList<String> pcbList) {
		
		System.out.println("---------------------------------");
		System.out.println("Moving process : "+ pid +" to Disk");
		System.out.println("---------------------------------");
		System.out.println("Printing Disk ");
		ArrayList<String> diskList = new ArrayList<String>();
		readFileIntoList("disk.txt", diskList);
		boolean exists = false;
		for (int i = 0; i < diskList.size(); i++) {
			String x = diskList.get(i);
			if (x.length() >= 5) {
				if(Character.toString(x.charAt(0)).equals("i")&&Character.toString(x.charAt(1)).equals("d"))
				{
					if (Character.getNumericValue(x.charAt(5)) == pid) {
					exists = true;
					break;
				}
				}
				
			}
		}
		if (exists == true) {
			moveFirstTime(pid, objectsList, lineList, pcbList, diskList);

		} else { // diskList.add("**");
			diskList.add("id = " + pid);
			diskList.add("----");

			for (int i = 0; i < pcbList.size(); i++) {
				diskList.add(pcbList.get(i));
			}
			diskList.add("----");
			// diskList.add("**");
			for (int i = 0; i < objectsList.size(); i++) {
				diskList.add("name " + objectsList.get(i).getName()); // please put only the variables you have
				diskList.add("value " + objectsList.get(i).getProcessData());
				diskList.add("----");

			}

			for (int i = 0; i < lineList.size(); i++) {
				diskList.add(lineList.get(i));

			}

			diskList.add("----");
			diskList.add("****");

		}
			printList(diskList);
		try {
			clearTheFile("disk.txt");
			System.out.println("---------------------------------");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			writeListIntoFile("disk.txt", diskList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clearTheFile(String fileName) throws IOException {
		FileWriter fwOb = new FileWriter(fileName, false);
		PrintWriter pwOb = new PrintWriter(fwOb, false);
		pwOb.flush();
		pwOb.close();
		fwOb.close();
	}

	private void moveFirstTime(int pid, ArrayList<ProcessObject> objectsList, ArrayList<String> lineList,
			ArrayList<String> pcbList, ArrayList<String> diskList) {
		int seperatorCount = 0;
		int start = 0;
		int end = 0;
		for (int i = 0; i < diskList.size(); i++) {
			if (seperatorCount == pid)
				start = i + 1;
			if (diskList.get(i).equals("****"))
				seperatorCount = seperatorCount + 1;

			if (seperatorCount == pid + 1)
				end = i;

		}
		ArrayList<String> listTochange = getProcessList(diskList, pid, 0, 0);// el 7etta el gowa el file //20 lines
		listTochange.remove(listTochange.size() - 1);
		listTochange.remove(listTochange.size() - 1);
		int ListTochangeSize = listTochange.size();
		// disk 42 lines
		ArrayList<String> editedList = new ArrayList<String>(); // el 7etta el ana 3ayza abdlha bel gowa //17 lines
		editedList.add("id = " + pid);
		editedList.add("----");

		for (int i = 0; i < pcbList.size(); i++) {
			editedList.add(pcbList.get(i));
		}

		editedList.add("----");

		for (int i = 0; i < objectsList.size(); i++) {
			editedList.add("name " + objectsList.get(i).getName()); // please put only the variables you have
			editedList.add("value " + objectsList.get(i).getProcessData());
			editedList.add("----");

		}
		for (int i = 0; i < lineList.size(); i++) {
			editedList.add(lineList.get(i));
		}

		editedList.add("----");

		// editedList.add("**");

		int end2 = start + editedList.size();

		if (ListTochangeSize > editedList.size()) {
			int difference = ListTochangeSize - editedList.size();
			int count = 0;
			int removenum = end - difference;
			for (int i = start; i < end2; i++) {
				diskList.set(i, editedList.get(count));
				count = count + 1;
			}
			for (int i = 0; i < difference; i++) {
				diskList.remove(removenum);
			}

		} else {
			if (ListTochangeSize < editedList.size()) {
				int count = 0;
				int difference = editedList.size() - ListTochangeSize;
				for (int i = start; i <= end; i++) {
					diskList.set(i, editedList.get(count));
					count = count + 1;
				}

				for (int i = end + 1; i <= end + difference; i++) {
					diskList.add(i, editedList.get(count));
					count = count + 1;
				}
			} else {
				if (ListTochangeSize == editedList.size()) {
					int count = 0;
					for (int i = start; i <= end; i++) {
						diskList.set(i, editedList.get(count));
						count = count + 1;
					}

				}
			}
		}

	}

	public void printDisk() {
		ArrayList<String> readDiskFile = new ArrayList<String>();
		readFileIntoList("disk.txt", readDiskFile);
		for (int i = 0; i < readDiskFile.size(); i++) {
			System.out.println(readDiskFile.get(i));
		}
	}

	protected DiskObj getFromDisk(int pid, ArrayList<String> diskList) {
		int start = 0;
		int end = 0;
		ArrayList<String> processList = getProcessList(diskList, pid, start, end);

		end = Integer.parseInt(processList.remove(processList.size() - 1));
		start = Integer.parseInt(processList.remove(processList.size() - 1));
		
		ArrayList<String> pcbList = getPcbList(processList); // shaghal
		ArrayList<String> objectList = getObjectList(processList);
		ArrayList<String> lineList = getLineList(processList, start, end);
		Collections.reverse(lineList);
		DiskObj diskObj = new DiskObj(lineList, pcbList, objectList);
		return diskObj;
	}

	protected void writeListIntoFile(String filePath, ArrayList<String> arr) throws IOException {
		FileWriter fw = new FileWriter(filePath, true);
		for (String str : arr) {
			fw.write(str + System.lineSeparator());
		}
		fw.close();
	}

	protected void readFileIntoList(String fileName, ArrayList<String> list) {
		BufferedReader br = null;
		try {
			FileReader fr = new FileReader(fileName);

			br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {
				list.add(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void printList(ArrayList<String> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}

	private ArrayList<String> getPcbList(ArrayList<String> diskList) {

		int seperatorCount = 0;
		ArrayList<String> returnedList = new ArrayList<String>();
		for (int i = 0; i < diskList.size(); i++) {
			if (seperatorCount == 1)
				returnedList.add(diskList.get(i));

			if (diskList.get(i).equals("----"))
				seperatorCount = seperatorCount + 1;

		}
		returnedList.remove(returnedList.size() - 1);
		return returnedList;
	}

	private ArrayList<String> getObjectList(ArrayList<String> diskList) {

		int seperatorCount = 0;
		ArrayList<String> returnedList = new ArrayList<String>();
		for (int i = 0; i < diskList.size(); i++) {
			String line = diskList.get(i);
			String[] splitted = line.split(" ");

			if (splitted[0].equals("name")) {

				String value = diskList.get(i + 1);
				String[] valueSplited = value.split(" ");
				String x = splitted[1] + " " + valueSplited[1];
				returnedList.add(x);
			}

//			if (seperatorCount == 2 || seperatorCount == 3 || seperatorCount == 4) {
//				returnedList.add(diskList.get(i));
//			}
//
//			if (diskList.get(i).equals("----"))
//				seperatorCount = seperatorCount + 1;

		}

		return returnedList;
	}

	private ArrayList<String> getLineList(ArrayList<String> diskList, int start, int end) {

		int seperatorCount = 0;

		int index = end - start - 1;
		ArrayList<String> returnedList = new ArrayList<String>();
		for (int i = end; i >= start; i--) {
			if (diskList.get(index).equals("----"))
				seperatorCount = seperatorCount + 1;

			if (seperatorCount == 0)
				returnedList.add(diskList.get(index));

			if (seperatorCount > 0)
				break;

			index--;

		}

		return returnedList;
	}

	protected ArrayList<String> getDiskList() {
		ArrayList<String> list = new ArrayList<String>();
		readFileIntoList("disk.txt", list);
		return list;
	}

	public ArrayList<String> getProcessList(ArrayList<String> diskList, int pid, int start, int end)// pid=1
	{
		int seperatorCount = 0;

		for (int i = 0; i < diskList.size(); i++) {
			if (seperatorCount == pid)
				start = i + 1;
			if (diskList.get(i).equals("****"))
				seperatorCount = seperatorCount + 1;

			if (seperatorCount == pid + 1)
				end = i;

		}


		ArrayList<String> returnedList = new ArrayList<String>();
		for (int i = start; i <= end; i++) {
			returnedList.add(diskList.get(i));
		}
		returnedList.add("" + start);
		returnedList.add("" + end);

		return returnedList;
	}

//	public static void main(String[] args) throws IOException {
//		Disk disk = new Disk();
//
//		ArrayList<ProcessObject> objectList = new ArrayList<ProcessObject>();
//		ArrayList<String> lineList = new ArrayList<String>();
//
//		ProcessObject obj1 = new ProcessObject("x4", 0);
//		ProcessObject obj2 = new ProcessObject("y3", 1);
//		ProcessObject obj3 = new ProcessObject("z2", 2);
//		ProcessObject obj4 = new ProcessObject("f2", 3);
//
//		objectList.add(obj1);
//		objectList.add(obj2);
//		objectList.add(obj3);
//		objectList.add(obj4);
//
//		lineList.add("nada1 nazeer1 yassar");
//		lineList.add("mariam1 tarek1");
//		lineList.add("abdullah1 shoeib1");
//		lineList.add("mohamed1 ahmed");
//		lineList.add("mohamed1 ahmed");
//
//		Pcb pcb = new Pcb(1);
//
//		pcb.setMemEnd(2);
//		pcb.setPc(3);
//		pcb.setProcessState(State.Start);
//		pcb.setMemStart(4);
//		ArrayList<String> pcb1 = new ArrayList<String>();
//		pcb1.add("hi nado");
//		pcb1.add("bye nado");
//		pcb1.add("red nado");
//		pcb1.add("no l nado");
//		pcb1.add("no l nado");
//		disk.moveToDisk(0, objectList, lineList, pcb1);
//		disk.moveToDisk(1, objectList, lineList, pcb1);// 1st time
//
//		disk.moveToDisk(2, objectList, lineList, pcb1);
//		disk.moveToDisk(2, objectList, lineList, pcb1);
//		disk.moveToDisk(3, objectList, lineList, pcb1);
//		disk.moveToDisk(0, objectList, lineList, pcb1);
//		disk.moveToDisk(1, objectList, lineList, pcb1);
//		disk.moveToDisk(2, objectList, lineList, pcb1);
//		
//
//			pcb1.add("hi");
//			lineList.add("bye");
//		disk.moveToDisk(0, objectList, lineList, pcb1);
//		disk.moveToDisk(1, objectList, lineList, pcb1);
//		disk.moveToDisk(2, objectList, lineList, pcb1);
//		
//		ArrayList<String> lineList1 = new ArrayList<String>();
//		disk.readFileIntoList("disk.txt", lineList1);
//		DiskObj r = disk.getFromDisk(1, lineList1);
//
//		System.out.println(r.getPcbList());
//		System.out.println(r.getObjectsList());
//		System.out.println(r.getLineList());
//
//	}
}