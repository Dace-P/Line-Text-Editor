import java.util.StringTokenizer;
import java.util.Scanner;
import java.io.*;

class LTE {

    public static Buffer clipboard = new Buffer();
    public static Buffer buffer = new Buffer();
    private static boolean exit = false;
    private static boolean lineToggle = false;
    private static CommandLine commandLine;

    public static void main(String[] args) throws FileNotFoundException   {
        try {
            Scanner scan = new Scanner(System.in);
            String fileName = scan.nextLine();
            buffer = new Buffer(fileName);
            buffer.bufferFileIn(fileName);
            while (!exit) {
        		commandLine = new CommandLine(scan.nextLine());
                process_command();
            }
            scan.close();

        } catch (ArrayIndexOutOfBoundsException e) {
            buffer = new Buffer();
            buffer.setFileSpec("empty");
            Scanner scan = new Scanner(System.in);

            while (!exit) {
				commandLine = new CommandLine(scan.nextLine());
                process_command();
            }
            scan.close();
        }
    }

    

    public static void process_command(){
        
        try {
        switch(commandLine.command) {
    case "h":
        help();
        break;
    case "r":
        readFile();
        break;
    case "w":
        writeToBuffer();
        break;
    case "f":
        changeFileName();
        break;
    case "q":
        quit();
        break;
    case "q!":
        forceQuit();
        break;
    case "t":
        top();
        break;
    case "b":
        bottom();
        break;
    case "g":
        goToLine();
        break;	
    case "-":
        prevLine();
        break;
    case "+":
        nextLine();
        break;
    case "=":
        printCurrLine();
        break;
    case "n":
        toggleLineNum();
        break;
    case "#":
        printContentSize(); 
        break;
    case "p":
        printLine();
        break;
    case "pr":
        printMultipleLines();
        break;
    case "?":
        searchBack();
        break;
    case "/":
        searchForward();
        break;
    case "s":
        substitute(); 
        break;
    case "sr":
        subBetween();   
        break;
    case "d":
        cut();   
        break;
    case "dr":
        cutLines();   
        break;
    case "c":
        copy();  
        break;
    case "cr":
        copyBetween(); 
        break;
    case "pa":
        pasteAbove(); 
        break;
    case "pb":
        pasteBelow();
        break;
    case "ia":
        insertAbove(); 
        break;
    case "ic":
        insertBetween();  
        break;
    case "ib":
        insertBelow();
        break;
    default:
        System.out.println("Invalid Command");
        break;
    }
    }   catch(ArrayIndexOutOfBoundsException e) {
    System.out.println("Error");
}

}

    public static void help() {
        // h

    }

    // Read a file into the current buffer works
    public static void readFile() {

        if (commandLine.tokenCount != 2) {
            System.out.println("Not A Valid Argument");
        }

        else {
            try { 
                BufferedReader read = new BufferedReader(new FileReader(commandLine.argu1));
                String line = read.readLine();
                buffer.text.clear();
                while (line != null) {
                    buffer.text.insertLast(line);
                    line = read.readLine();
                }
                
            } catch (FileNotFoundException e) {
                System.out.println("File Not Found");
            } catch (IOException e) {
                System.out.println("Write a working file name");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Write a working file name");
            }
        }
    }

    // Write the current buffer to a file on disk works
    public static void writeToBuffer() {
        // w
        if(commandLine.tokenCount != 1) {
			System.out.println("Not A Valid Argument");
		}
		else {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(buffer.getFileSpec()));
				if(buffer.text.isEmpty()) {
					System.out.println("Buffer is Empty");
				}
				else {
					buffer.text.seek(0);
					while(!buffer.text.atLast()) {
						writer.write(buffer.text.getData() + "\n");
						buffer.text.next();
					}
					writer.close();
					}
			} catch (IOException e) {
				
			}
			buffer.setDirty(false);
		}

    }

    // Change the name of the current buffer 
    public static void changeFileName() {
        // f
        if(commandLine.tokenCount != 2) {
			System.out.println("Not A Valid Argument");
		}
		else {
			buffer.setFileSpec(commandLine.argu1);
			buffer.setDirty(true);
        }
    }
    public static void quit() {
        if(commandLine.tokenCount != 1) {
			System.out.println("Not A Valid Argument");
		}
		else {
			if(buffer.getDirty()) {
				System.out.println("Do You Want to Save Before Quitting? y/n");
				Scanner s = new Scanner(System.in);
				String str = s.nextLine();
				if(str.equals("y")) {
					writeToBuffer();
					
					exit = true;
				}
				else if(str.endsWith("n")){
					exit = true;
				}
				else {
					System.out.println("Not a Valid Response");
				}
				s.close();
			}
			else {
				exit = true;
			}
		}
    }
    //works
    public static void forceQuit() {
        if(commandLine.tokenCount != 1) {
			System.out.println("Not A Valid Argument");
		}
		else {
			exit = true;
		}
    }

    // Go to the first line of the buffer works
    public static void top() {
        // t
        if(commandLine.tokenCount != 1) {
			System.out.println("Not A Valid Argument");
		}
		else {
			if(buffer.text.isEmpty()) {
				System.out.println("Buffer is Empty");
			}
			else {
				buffer.text.seek(0);
			}
		}
    }

    // go to the last line of the buffer mine works
    public static void bottom() {
        // b
        if(commandLine.tokenCount != 1) {
			System.out.println("Not A Valid Argument");
		}
			else {
				if(buffer.text.isEmpty()) {
					System.out.println("Buffer is Empty");
				}
				else {
					buffer.text.seek(buffer.text.getSize() -1);
				}
		}
    }

    // Go the line num of the buffer mine Works
    public static void goToLine() {
        // g num
        if(commandLine.tokenCount != 2) {
			System.out.println("Not A Valid Argument");
		}
		else {
			try {
				int argument = Integer.parseInt(commandLine.argu1);
			if(buffer.text.isEmpty()) {
				System.out.println("Buffer is Empty");
			}
			else if(argument > buffer.text.getSize() || argument <= 0) {
				System.out.println("Range must be in [1," + buffer.text.getSize() + "]");
			}
			else {
				buffer.text.seek(argument - 1);
			}
			}catch(NumberFormatException e) {
				System.out.println("Invalid Argument Passed");
			}
		}
    }

    // Go to the previous line works
    public static void prevLine() {
        // -
        if(commandLine.tokenCount != 1) {
			System.out.println("Not A Valid Argument");
		}
		else {
			if(buffer.text.isEmpty()) {
				System.out.println("Buffer is Empty");
			}
			else if(buffer.text.atFirst()) {
				System.out.println("Already at the top of the buffer");
			}
			else {
				buffer.text.previous();
			}
		}
    }

    // Go to next line Works
    public static void nextLine() {
        // +
        if(commandLine.tokenCount != 1) {
			System.out.println("Not A Valid Argument");
		}
		else {
			if(buffer.text.isEmpty()) {
				System.out.println("Buffer is Empty");
			}
			else if(buffer.text.atLast()) {
				System.out.println("Already at the bottom of the buffer");
			}
			else {
				buffer.text.next();
			}
		}
    }

    // Print the current line number Works
    public static void printCurrLine() {
        // =
        if(commandLine.tokenCount != 1) {
			System.out.println("Not A Valid Argument");
		}
		else {
			if(buffer.text.isEmpty()) {
				System.out.println("Buffer is Empty");
			}
			else {
				System.out.println(buffer.text.getIndex() + 1);
			}
		}
    }

    // Toggle line number displayed
    public static void toggleLineNum() {
        // n
        if(commandLine.tokenCount != 1) {
			System.out.println("Not A Valid Argument");
		}
		else {
			lineToggle = !lineToggle;
		}
    }

    // Print the number of lines and characters in the buffer Works
    public static void printContentSize() {
        // #
        if(commandLine.tokenCount != 1) {
			System.out.println("Not A Valid Argument");
		}
		else {
			if(buffer.text.isEmpty()){
				System.out.println("Buffer is Empty");
			}
			else {
				int num = 0;
				buffer.text.seek(0);
				while(!buffer.text.atLast()) {
					num += buffer.text.getData().length();
					buffer.text.next();
				}
				System.out.print("L:(" + buffer.text.getSize() + ") ");
				System.out.println("C:("+ num + ")");
			}
		}
    }

    // Print the current line
    public static void printLine() {
        // p
        if(commandLine.tokenCount != 1) {
			System.out.println("Not A Valid Argument");
		}
		else {
			if(buffer.text.isEmpty()) {
				System.out.println("Buffer is Empty");
			}
			else {
				System.out.println(buffer.text.getData());	
			}
		}
    }

    // Print several lines works
    public static void printMultipleLines() {
        // pr start stop
        if(commandLine.tokenCount != 3) {
			System.out.println("Not A Valid Argument");
		}
		else {
			try {
			int start = Integer.parseInt(commandLine.argu1); int stop = Integer.parseInt(commandLine.argu2);
			if(buffer.text.isEmpty()) {
				System.out.println("Buffer is Empty");
			}
			else if(start < 1 || start > buffer.text.getSize() || stop < 1 || stop > buffer.text.getSize()) {
				System.out.println("Error - Values must be in [1," + buffer.text.getSize() + "]");
			}
			else if(start > stop) {
				System.out.println("Start cannot be larger than stop");
			}
			else {
				buffer.text.seek(start - 1);
				while(start != stop + 1) {
					System.out.println(buffer.text.getData());
					buffer.text.next();
					start++;
				}
				
			}
			}catch(NumberFormatException e) {
				System.out.println("Invalid Argument ");
			}
		}
    }

    // Search backwards for a pattern
    public static void searchBack() {
        // ? pattern
        if(commandLine.tokenCount != 2) {
			System.out.println("Inavlid Amount of Arguments ");
		}
		else{
			String pattern = commandLine.argu1;
			int ind = buffer.text.getIndex();
			boolean found = false;
			boolean start = false;
			if(buffer.text.isEmpty()) {
				System.out.println("Buffer is Empty");
			}
			else if(buffer.text.atFirst()) {
				System.out.println("Backwards Search Cannot be Used on First Line");
			}
			else {
				buffer.text.previous();
				while(found == false && start == false) {
					StringTokenizer tok = new StringTokenizer(buffer.text.getData());
					while(tok.hasMoreTokens()) {
						String token = tok.nextToken();
						if(token.equals(pattern)) {
							found = true;
							buffer.text.seek(buffer.text.getIndex());
						}
					}
					if(found == false) {
					buffer.text.previous();
					}
					if(buffer.text.atFirst()) {
						start = true;
					}
				}
				if(found == false) {
					buffer.text.seek(ind);
					System.out.println("String " + pattern + " Not Found");
				}
			}
		}
    }

    // Search forward for a pattern works
    public static void searchForward() {
        /// pattern
        if(commandLine.tokenCount != 2) {
			System.out.println("Inavlid Amount of Arguments ");
		}
		else{
			String pattern = commandLine.argu1;
			int ind = buffer.text.getIndex();
			boolean found = false;
			boolean end = false;
			if(buffer.text.isEmpty()) {
				System.out.println("Buffer is Empty");
			}
			else if(buffer.text.atLast()) {
				StringTokenizer tok = new StringTokenizer(buffer.text.getData());
				while(tok.hasMoreTokens()) {
					String token = tok.nextToken();
					if(token.equals(pattern)) {
						found = true;
					}
				}
				System.out.println(found);
				if(found == true) {
					buffer.text.seek(buffer.text.getSize() - 1);	
				}
				else {
					System.out.println("String " + pattern + " Not Found");
				}
			}
			else {
				while(found == false && end == false) {
					StringTokenizer tok = new StringTokenizer(buffer.text.getData());
					while(tok.hasMoreTokens()) {
						String token = tok.nextToken();
						if(token.equals(pattern)) {
							found = true;
							buffer.text.seek(buffer.text.getIndex());
						}
					}
					if(found == false) {
					buffer.text.next();
					}
					if(buffer.text.atLast()) {
						end = true;
					}
				}
				if(found == false) {
					buffer.text.seek(ind);
					System.out.println("String " + pattern + " Not Found");
				}
			}
		}
    }

    // Substitute all occurrences of text1 with text2 on current line
    public static void substitute() {
        // s text1 text2
        if(commandLine.tokenCount != 3) {
			System.out.println("Not A Valid Argument");
		}
		else {
			StringTokenizer tok = new StringTokenizer(buffer.text.getData(), " ");
			String text1 = commandLine.argu1;
			String text2 = commandLine.argu2;
			String newLine = "";
			if(buffer.text.isEmpty()) {
				System.out.println("Buffer is Empty");
			}
			else {
				while(tok.hasMoreTokens()) {
					String token = tok.nextToken();
					if(token.equals(text1)) {
						newLine = newLine + " " + text2;
					}
					else {
						newLine = newLine + " " + token;
					}
				}
				buffer.text.setData(newLine.substring(1));
				buffer.setDirty(true);
			}
		}
    }

    // Substitute all occurrences of text1 with text2 on current line
    public static void subBetween() {
        // sr text1 text2 start stop
        if(commandLine.tokenCount != 5) {
			System.out.println("Not A Valid Argument");
		}
		else {
			try {
			int start = Integer.parseInt(commandLine.argu1);
			int stop = Integer.parseInt(commandLine.argu2);
			String text1 = commandLine.argu3;
			String text2 = commandLine.argu4;
			String newLine = "";
			if(buffer.text.isEmpty()) {
				System.out.println("Buffer is Empty");
			}
			else if(start < 1 || start > buffer.text.getSize() || stop < 1 || stop > buffer.text.getSize()) {
				System.out.println("Range Error - start stop must be [1," + buffer.text.getSize() + "]");
			}
			else if(start > stop) {
				System.out.println("Start Must be Less Than Stop");
			}
			else {
				buffer.text.seek(start - 1);
				while(start != stop + 1) {
				StringTokenizer tok = new StringTokenizer(buffer.text.getData(), " ");
				while(tok.hasMoreTokens()) {
					String token = tok.nextToken();
					if(token.equals(text1)) {
						newLine = newLine + " " + text2;
					}
					else {
						newLine = newLine + " " + token;
					}
				}
				buffer.text.setData(newLine.substring(1));
				newLine = "";
				buffer.text.next();
				start++;
				}
				buffer.setDirty(true);
			}
			}catch(NumberFormatException e) {
				System.out.println("Invalid Indices Passed");
			}
		}
    }

    public static void cut() {
        // d
        if(commandLine.tokenCount != 1) {
			System.out.println("Not A Valid Argument");
		}
		else {
			if(buffer.text.isEmpty()) {
				System.out.println("Buffer Is Empty");
			}
			else {
				if(clipboard.text.isEmpty()) {
					clipboard.text.insertLast(buffer.text.getData());
					buffer.text.delete(buffer.text.current);
					buffer.setDirty(true);
				}
				else {
					clipboard.text.clear();
					clipboard.text.insertLast(buffer.text.getData());
					buffer.text.delete(buffer.text.current);
					buffer.setDirty(true);
				}
			}
		}
    }

    public static void cutLines() {
        // dr start stop
        if(commandLine.tokenCount != 3) {
			System.out.println("Not A Valid Argument");
		}
		else {
			if(buffer.text.isEmpty()) {
				System.out.println("Buffer Is Empty");
			}
			else {
				try {
					clipboard.text.clear();
					int start = Integer.parseInt(commandLine.argu1); int stop = Integer.parseInt(commandLine.argu2);
					if(start < 1 || stop > buffer.text.getSize() || stop < 1 || start > buffer.text.getSize()) {
						System.out.println("Indices out of range");
					}
					else if(start > stop){
						System.out.println("Start Must be Less Than Stop");
					}
					else{
						buffer.text.seek(start - 1);
						while(start != stop + 1) {
							clipboard.text.insertLast(buffer.text.getData());
							buffer.text.delete(buffer.text.current);
							start++;
						}
						buffer.setDirty(true);
					}
				}catch(NumberFormatException e) {
					System.out.println("Invalid Argument Passed");
				}
			}
			
		}
    }

    public static void copy() {
        // c
        if(commandLine.tokenCount != 1) {
			System.out.println("Not A Valid Argument");
		}
		else {
			if(buffer.text.isEmpty()) {
				System.out.println("Buffer is Empty");
			}
			else {
				clipboard.text.clear();
				clipboard.text.insertLast(buffer.text.getData());
			}
		}
    }

    public static void copyBetween() {
        // cr start stop
        if(commandLine.tokenCount != 3) {
			System.out.println("Not A Valid Argument");
		}
		else {
			if(buffer.text.isEmpty()) {
				System.out.println("Buffer is Empty");
			}
			else {
				try {
					clipboard.text.clear();
					int start = Integer.parseInt(commandLine.argu1); int stop = Integer.parseInt(commandLine.argu2);
					if(start < 1 || stop > buffer.text.getSize()) {
						System.out.println("Indices out of range");
					}
					else if(start > stop){
						System.out.println("Start Must be Less Than Stop");
					}
					else{
						buffer.text.seek(start - 1);
						while(start != stop + 1) {
							clipboard.text.insertLast(buffer.text.getData());
							buffer.text.next();
							start++;
						}
					}
				}catch(NumberFormatException e) {
					System.out.println("Invalid Argument Passed");
				}
			}
		}
    }

    public static void pasteAbove() {
        // pa
        if(commandLine.tokenCount != 1) {
			System.out.println("Not A Valid Argument");
		}
		else {
			if(clipboard.text.isEmpty()) {
				System.out.println("Clipboard Is Empty");
			}
			else if(buffer.text.atFirst()) {
				clipboard.text.seek(0);
				if(clipboard.text.size == 1) {
					buffer.text.insertFirst(clipboard.text.getData());
					buffer.setDirty(true);
				}
				else {
					buffer.text.insertFirst(clipboard.text.getData());
					buffer.text.next();
					clipboard.text.next();
					while(!clipboard.text.atLast()) {
						buffer.text.insertAt(clipboard.text.getData());
						buffer.text.next();
						clipboard.text.next();
					}
					buffer.text.insertAt(clipboard.text.getData());
					buffer.setDirty(true);
				}
			}
			else {
				clipboard.text.seek(0);
				if(clipboard.text.size == 1) {
					//buffer.text.previous();
					buffer.text.insertAt(clipboard.text.getData());
					buffer.setDirty(true);
				}
				else {
					while(!clipboard.text.atLast()) {
						buffer.text.insertAt(clipboard.text.getData());
						buffer.text.next();
						clipboard.text.next();
					}
					buffer.text.insertAt(clipboard.text.getData());
					buffer.setDirty(true);
				}
			}
			
		}
    }

    public static void pasteBelow() {
        // pb
        if(commandLine.tokenCount != 1) {
			System.out.println("Not A Valid Argument");
		}
		else {
			if(clipboard.text.isEmpty()) {
				System.out.println("Clipboard Is Empty");
			}
			else if(buffer.text.atLast()) {
				clipboard.text.seek(0);
				buffer.text.insertLast(clipboard.text.getData());
				clipboard.text.next();
				while(!clipboard.text.atLast()) {
					buffer.text.insertLast(clipboard.text.getData());
					//buffer.text.next();
					clipboard.text.next();
				}
				buffer.text.insertLast(clipboard.text.getData());
				buffer.setDirty(true);
			}
			else {
				clipboard.text.seek(0);
				if(clipboard.text.size == 1) {
					buffer.text.next();
					buffer.text.insertAt(clipboard.text.getData());
					buffer.setDirty(true);
				}
				else {
					buffer.text.next();
					while(!clipboard.text.atLast()) {
						buffer.text.insertAt(clipboard.text.getData());
						buffer.text.next();
						clipboard.text.next();
					}
					buffer.text.insertAt(clipboard.text.getData());
					buffer.setDirty(true);
				}
			}
			
		}
    }

    public static void insertAbove() {
        // pb
        if(commandLine.tokenCount != 1) {
			System.out.println("Invalid Arguments");
		}
		else {
			Scanner scan = new Scanner(System.in);
			System.out.println("Insert Lines: ");
			DLList<String> temp = new DLList<String>();
			boolean dot = false;
			int pos = buffer.text.getIndex();
			while(dot == false) {
				temp.insertLast(scan.nextLine());
				String val = temp.getData();
				if(val.equals(".")) {
					dot = true;
					temp.deleteLast();
				}
				else {
					dot = false;
				}
			}
			if(buffer.text.atFirst()) {
				temp.seek(0);
				if(temp.getSize() == 1) {
					buffer.text.insertFirst(temp.getData());
					buffer.setDirty(true);
				}
				else {
					int i = 0;
					while(i != temp.getSize()) {
						buffer.text.insertAt(temp.getData());
						buffer.text.next();
						temp.next();
						i++;
					}
					buffer.setDirty(true);
				}
				buffer.text.seek(0);
			}
			else {
				temp.seek(0);
				int i = 0;
				while(i != temp.getSize()) {
					buffer.text.insertAt(temp.getData());
					buffer.text.next();
					temp.next();
					i++;
				}
				buffer.text.seek(pos);
				buffer.setDirty(true);
			}
		}
		
    }
    public static void insertBetween(){
        if(commandLine.tokenCount != 1) {
			System.out.println("Invalid Arguments");
		}
		else {
			Scanner scan = new Scanner(System.in);
			System.out.println("Insert Lines: ");
			DLList<String> temp = new DLList<String>();
			boolean dot = false;
			int pos = buffer.text.getIndex();
			while(dot == false) {
				temp.insertLast(scan.nextLine());
				String val = temp.getData();
				if(val.equals(".")) {
					dot = true;
					temp.deleteLast();
				}
				else {
					dot = false;
				}
			}
			if(buffer.text.atLast()) {
				temp.seek(0);
				int i = 0;
				while(i != temp.getSize()) {
					buffer.text.insertLast(temp.getData());
					temp.next();
					i++;
				}
				buffer.text.seek(pos + 1);
				buffer.setDirty(true);
			}
			else {
				temp.seek(0);
				int i = 0;
				buffer.text.next();
				while(i != temp.getSize()) {
					buffer.text.insertAt(temp.getData());
					buffer.text.next();
					temp.next();
					i++;
				}
				buffer.text.seek(pos + 1);
				buffer.setDirty(true);
			}
		}
    }

    public static void insertBelow() {
        // pb
        if(commandLine.tokenCount != 1) {
			System.out.println("Invalid Arguments");
		}
		else {
			Scanner scan = new Scanner(System.in);
			System.out.println("Insert Lines: ");
			DLList<String> temp = new DLList<String>();
			boolean dot = false;
			int ind = buffer.text.getIndex();
			while(dot == false) {
				temp.insertLast(scan.nextLine());
				String val = temp.getData();
				if(val.equals(".")) {
					dot = true;
					temp.deleteLast();
				}
				else {
					dot = false;
				}
			}
			if(buffer.text.atLast()) {
				temp.seek(0);
				int i = 0;
				while(i != temp.getSize()) {
					buffer.text.insertLast(temp.getData());
					temp.next();
					i++;
				}buffer.text.seek(buffer.text.getSize() - 1);
				buffer.setDirty(true);
			}
			else {
				temp.seek(0);
				int i = 0;
				buffer.text.next();
				while(i != temp.getSize()) {
					buffer.text.insertAt(temp.getData());	
					buffer.text.next();
					temp.next();
					i++;
				}
				buffer.text.seek(ind + temp.size);
				buffer.setDirty(true);
			}
		}
    }
	
}
