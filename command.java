import java.io.*;
import java.util.*;

public class command {
    mips mip;
    String file;
    String script;
    int type;       // 0: commandline scripts
                    // 1: file scripts

    public command(String[] args, object o){
        this.mip = new mips(o);
        this.file = args[0];

        if(args.length == 1){
            this.script = null;
            this.type = 0;
        }else if(args.length == 2){
            this.script = args[1];
            this.type = 1;
        }else{
            System.out.println("invalid input");
            System.exit(1);
        }
    }

    public void runCommands()throws Exception{
        boolean running = true;
        String string;
        if(this.type == 1){
            File file = new File(this.script);
            BufferedReader buffer = new BufferedReader(new FileReader(file));
            while ((string = buffer.readLine()) != null && running){
                System.out.println("mips> " + string);
                running = parse(string);
            }
            buffer.close();

        }else if(this.type == 0){
            Scanner input = new Scanner(System.in);
            while(running){
                System.out.print("mips> ");
                running = parse(input.nextLine());
            }
            input.close();
        }
    }

    private boolean parse(String in){
        String temp[] = in.split(" ");
        switch(temp[0]){
            case "debug"://debug code
                commanddebug(Integer.parseInt(temp[1])); //debug code
                break;
            case "h":
                help();
                break;

            case "d":
                dump();
                break;
            case "p":
                pipeline();
                break;
            case "s":
                if(temp.length == 1){
                    commandS(1);
                }else{
                    commandS(Integer.parseInt(temp[1]));
                }
                break;
            
            case "r":
                commandR();
                break;

            case "m":
                displayMem(Integer.parseInt(temp[1]),Integer.parseInt(temp[2]));
                break;
        
            case "c":
                clear();
                break;
            
            case "q":
                return false;
        }
        return true;
    }

    private void help(){
        System.out.println("\nh = show help");
        System.out.println("d = dump register state");
        System.out.println("p = show pipeline registers");
        System.out.println("s = single step through the program (i.e. execute 1 instruction and stop)");
        System.out.println("s num = step through num instructions of the program");
        System.out.println("r = run until the program ends");
        System.out.println("m  num1 num2 = display data memory from location num1 to num2");
        System.out.println("c = clear all registers, memory, and the program counter to 0");
        System.out.println("q = exit the program\n");
    }
    private void dump(){
        System.out.println("\npc = " +mip.pc);
        System.out.println("$0 = " + mip.reg[0] +"\t\t$v0 = "+ mip.reg[2] +"\t\t$v1 = "+ mip.reg[3] +"\t\t$a0 = "+mip.reg[4]);
        System.out.println("$a1 = " + mip.reg[5] +"\t\t$a2 = "+ mip.reg[6] +"\t\t$a3 = "+ mip.reg[7] +"\t\t$t0 = "+mip.reg[8]);
        System.out.println("$t1 = " + mip.reg[9] +"\t\t$t2 = "+ mip.reg[10] +"\t\t$t3 = "+ mip.reg[11] +"\t\t$t4 = "+mip.reg[12]);
        System.out.println("$t5 = " + mip.reg[13] +"\t\t$t6 = "+ mip.reg[14] +"\t\t$t7 = "+ mip.reg[15] +"\t\t$s0 = "+mip.reg[16]);
        System.out.println("$s1 = " + mip.reg[17] +"\t\t$s2 = "+ mip.reg[18] +"\t\t$s3 = "+ mip.reg[19] +"\t\t$s4 = "+mip.reg[20]);
        System.out.println("$s5 = " + mip.reg[21] +"\t\t$s6 = "+ mip.reg[22] +"\t\t$s7 = "+ mip.reg[23] +"\t\t$t8 = "+mip.reg[24]);
        System.out.println("$t9 = " + mip.reg[25] +"\t\t$sp = "+ mip.reg[29] +"\t\t$ra = "+ mip.reg[31]+"\n");
    }
    private void pipeline(){
        System.out.println("\npc      if/id   id/exe  exe/mem mem/wb");
        System.out.println(mip.pc + "\tempty\tempty\tempty\tempty\n"); //replace empty with pipline regs
    }
    private void commandS(int N){
        mip.step(N);
        pipeline();
    }
    private void commandR(){
        mip.run();
    }
    private void commanddebug(int t){
        mip.debugrun(t);
    }
    private void displayMem(int low, int high){
        for(int i = low; i <= high; i++){
            System.out.println("["+i+"] = " + mip.mem[i]);
        }
    }
    private void clear(){
        for(int i = 0 ;i< 8192; i++){
            mip.mem[i] = 0;
        }
        for(int i = 0; i < 32; i++){
            mip.reg[i] = 0;
        }
        mip.pc = 0;
        System.out.println("\tSimulator reset\n");

    }
}
