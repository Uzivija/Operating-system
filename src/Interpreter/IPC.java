package Interpreter;
import java.io.FileNotFoundException;
import java.util.*;


public class IPC {
    public static final Vector<PipeQueue> Pipes = new Vector<>();
    public final Vector<Integer> descriptor = new Vector<>();


    public IPC() throws FileNotFoundException {
    }

    public Vector<PipeQueue> getPipes() {
        return Pipes;
    }

    public Vector<Integer> getDescriptor() {
        return descriptor;
    }

    public static void Scan() {
        Scanner myObj1 = new Scanner(System.in);
        System.out.println("Wprowadz dane:");
        String c = myObj1.nextLine();
        if (c == "\n ") {
            VirtualMemory.saveString(Shell.getPM().init, 12, "0");
        } else
            VirtualMemory.saveString(Shell.getPM().init, 5, c);
        //Memory.printRawRam();

    }

    public int writeToPipe(int fd, Vector<Character> buffer, int nchar) {
        Scan();
//        Scanner myObj1 = new Scanner(System.in);
//System.out.println("Wpisz dane:\n");
//        String c = myObj1.nextLine();
//        VirtualMemory.saveString(Shell.getPM().init, 5, c);
//        Memory.printRawRam();
        int written = 0;

        for (PipeQueue e : Pipes) {

            if (e.descW == fd) {

                if (1 > 0) {


                    for (int i = 0; i < 4; i++) { //buffer.size()
                        if (VirtualMemory.readChar(Shell.getPM().init, 5) == ' ') {
                            VirtualMemory.saveString(Shell.getPM().init, 12, "0");
                        }
                        if (VirtualMemory.readChar(Shell.getPM().init, 5 + i) == ' ') {
                            break;
                        } else {
                            char x = VirtualMemory.readChar(Shell.getPM().init, 5 + i);
                            Memory.writePipe(x, i);
                            written++;
                        }

                    }
                    if (VirtualMemory.readChar(Shell.getPM().init, 5) == ' ') {
                        Memory.writePipe((char) 0, 0);

                    }

                } else if (nchar > 4 - e.eQueue.size()) {
                    Memory.writePipe((char) written, 0);
                    Memory.printRawRam();
                    //System.out.println(a);
                } else if (nchar == 0) {
                    System.out.println("Nic do odczytu, zamykam pipe");
                    Memory.writePipe((char) 0, 0);
                    // Memory.printRawRam();
                    //System.out.println(a);
                }
               /* else if (buffer.capacity()>4){
                    break;
                }*/
            }

            //  Memory.printRawRam();
        }
        return written;
    }

    public static void readFromPipe() {
        int tmp = 1;
        int read = 0;
        for (PipeQueue e : Pipes) {

            if (1 > 0) {
                System.out.println("Wpisz ile znakow chcesz odczytac:");
                Scanner c = new Scanner(System.in);
                int d = c.nextInt();
                tmp = d;
                if (d > 4) {
                    do {
                        System.out.println("Wybierz liczbe z przedzialu  0 - 4! ");
                        d = c.nextInt();
                    } while (d > 4);
                }
                for (int i = 0; i < d; i++) { //buffer.size()
                    //char x = VirtualMemory.readChar(Shell.getPM().init, 5 + i);
                    char x =  Memory.readPipe(i);
                    Memory.writePipe(x, 5 + i);
                    read++;
                }
                if (VirtualMemory.readChar(Shell.getPM().init, 5) == ' ') {
                    Memory.writePipe((char) 0, d + 5);
                    System.out.println("Ten pipe jest pusty");
                    //break;
                }

            }

        }
        Memory.readPipeFrameOdczyt(tmp);
        // System.out.println("Odczyt z pipe'a:" );
        // Memory.printRawRam();
    }
    //System.out.print("Reading Pipe:  ");


    public static void closePipe() {
        Memory.clearPIPE();
        System.out.println("Pipe zostal zamkniety");
        // Memory.printRawRam();

    }

    public static void close(int pdesc) {
        //pdesc =null;
        System.out.println("The descriptor is closed\n");
    }

    public int Pipe(int[] pdesc) {
        Random rand = new Random();
        int read = rand.nextInt(4);
        int write = rand.nextInt(4);

        while (descriptor.contains(write)) {
            write = rand.nextInt(4);
        }
        write = pdesc[1];
        descriptor.add(write);

        while (descriptor.contains(read)) {
            read = rand.nextInt(4);
        }
        read = pdesc[0];
        descriptor.add(read);

        PipeQueue queueToAdd = new PipeQueue(write, read);
        Pipes.add(queueToAdd);
        return 0;
    }

    public static void WritePipe() throws FileNotFoundException {
        int[] pdesc = new int[2];
        IPC a = new IPC();
        a.Pipe(pdesc);
        Vector<Character> be = new Vector<Character>(4);
        Vector<Character> en = new Vector<Character>(4);

        for (int i = 0; i < 5; i++) {
            be.add('a');
        }


        if (be.size() > 4) {
            be.setSize(4);
        }
        if (be.size() == 1) {
            System.out.println("No pipe");
        }
        for (int i = 1; i < 6; i++) {
            // Memory.writePipe(be.get(), i);
        }
        a.writeToPipe(pdesc[1], be, 1);
        System.out.println("Zapis do pipe" + Memory.readPipeFrame());
        // close(pdesc[1]);
        // en.add(c);
    }

    public static void ReadPipe() {
        //VirtualMemory.readChar(PCB proces, int adress);
        //Memory.writePipe(b, adress);
        Vector<Character> en = new Vector<Character>(4);
   /* P5.pipe.readFromPipe(pdesc[0], en, 1);
    P5.pipe.readFromPipe(pdesc[0], en, 1);
    P5.pipe.readFromPipe(pdesc[0], en, 1);
    P5.pipe.readFromPipe(pdesc[0], en, 1);*/

        System.out.println(en);
        Memory.readPipe(1);
   /* Memory.readPipeFrame();
    P6.pipe.close(pdesc[0]);
    P5.pipe.close(pdesc[1]);*/
        // Memory.printRawRam();


    }

    public static void CreatePipe() {
        System.out.println("Utworzono pipe'a");
        Memory.writePipe(' ', 4);
    }
}