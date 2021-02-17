import java.io.*;
import java.util.Arrays;

public class mips {
    int mem[];
    int reg[];
    int pc;
    object instruct;
    pipeline p;

    public mips(object i){
        this.mem = new int[8192];
        this.reg = new int[32];
        this.pc = 0;
        this.instruct = i;
        this.p = new pipeline();
    }

    public void step(int N)
    {
        int i = 0;
        while (i < N && pc < instruct.twoD.size())
        {
            String name = instruct.twoD.get(pc)[0];
            callFunction(name, instruct.twoD.get(pc));
            i++;
            pc++;

            pipePal(name);
        }
    }

    public void run()
    {
        while(pc < instruct.twoD.size())
        {
            String name = instruct.twoD.get(pc)[0];
            callFunction(instruct.twoD.get(pc)[0], instruct.twoD.get(pc));
            pc++;

            pipePal(name);

        }
    }

    public void pipePal(String name)
    {
        p.pipe[3] = p.pipe[2];
        p.pipe[2] = p.pipe[1];
        p.pipe[1] = p.pipe[0];
        p.pipe[0] = name;
        p.cycles++;
        p.instructions++;
    }
    
    // debug code ----------------------------------------------------
    public void debugrun(int size)
    {

        try{
            int i = 0;
            FileWriter myFile = new FileWriter("debug.txt");
            while(pc < instruct.twoD.size() &&  i < size)
            {
                debugdump(this, myFile);
                callFunction(instruct.twoD.get(pc)[0], instruct.twoD.get(pc));
                pc++;
                i++;
            }
            myFile.close();
        }catch(IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void debugdump(mips mip, FileWriter file){
        try{
            file.write("\n" + Arrays.toString(mip.instruct.twoD.get(mip.pc)));
            file.write("\npc = " +mip.pc+"\n");
            file.write("$0 = " + mip.reg[0] +"|$v0 = "+ mip.reg[2] +"|$v1 = "+ mip.reg[3] +"|$a0 = "+mip.reg[4]+"\n");
            file.write("$a1 = " + mip.reg[5] +"|$a2 = "+ mip.reg[6] +"|$a3 = "+ mip.reg[7] +"|$t0 = "+mip.reg[8]+"\n");
            file.write("$t1 = " + mip.reg[9] +"|$t2 = "+ mip.reg[10] +"|$t3 = "+ mip.reg[11] +"|$t4 = "+mip.reg[12]+"\n");
            file.write("$t5 = " + mip.reg[13] +"|$t6 = "+ mip.reg[14] +"|$t7 = "+ mip.reg[15] +"|$s0 = "+mip.reg[16]+"\n");
            file.write("$s1 = " + mip.reg[17] +"|$s2 = "+ mip.reg[18] +"|$s3 = "+ mip.reg[19] +"|$s4 = "+mip.reg[20]+"\n");
            file.write("$s5 = " + mip.reg[21] +"|$s6 = "+ mip.reg[22] +"|$s7 = "+ mip.reg[23] +"|$t8 = "+mip.reg[24]+"\n");
            file.write("$t9 = " + mip.reg[25] +"|$sp = "+ mip.reg[29] +"|$ra = "+ mip.reg[31]+"\n");
        } catch (IOException e){
            System.out.println("An error occurred on the write step.");
            e.printStackTrace();
        }
    }

    // debug code -------------------------------------------------------

    public void callFunction(String name, String[] line)
    {
        switch (name){
            // R-INSTRUCTIONS
            case "add":
                add(line[1], line[2], line[3]);
                break;
            case "and":
                and(line[1], line[2], line[3]);
                break;
            case "or":
                or(line[1], line[2], line[3]);
                break;
            case "sub":
                sub(line[1], line[2], line[3]);
                break;
            case "sll":
                sll(line[1], line[2], line[3]);
                break;
            case "slt":
                slt(line[1], line[2], line[3]);
                break;
            case "jr":
                jr(line[1]);
                break;
            // I-INSTRUCTIONS
            case "addi":
                addi(line[1], line[2], line[3]);
                break;
            case "beq":
                beq(line[1], line[2], line[3]);
                break;
            case "bne":
                bne(line[1], line[2], line[3]);
                break;
            case "lw":
                lw(line[1], line[2], line[3]);
                break;
            case "sw":
                sw(line[1], line[2], line[3]);
                break;
            //J-INSTRUCTIONS
            case "j":
                j(line[1]);
                break;
            case "jal":
                jal(line[1]);
                break;
        }

    }

    // R-INSTRUCTIONS
    private void add(String RD, String RS, String RT)
    {
        int rd = getRegister(RD);
        int rs = getRegister(RS);
        int rt = getRegister(RT);

        reg[rd] = reg[rs] + reg[rt];
    }

    private void and(String RD, String RS, String RT)
    {
        int rd = getRegister(RD);
        int rs = getRegister(RS);
        int rt = getRegister(RT);

        reg[rd] = reg[rs] & reg[rt];
    }

    private void or(String RD, String RS, String RT)
    {
        int rd = getRegister(RD);
        int rs = getRegister(RS);
        int rt = getRegister(RT);

        reg[rd] = reg[rs] | reg[rt];
    }

    private void sub(String RD, String RS, String RT)
    {
        int rd = getRegister(RD);
        int rs = getRegister(RS);
        int rt = getRegister(RT);

        reg[rd] = reg[rs] - reg[rt];
    }

    private void sll(String RD, String RT, String SHAMT)
    {
        int rd = getRegister(RD);
        int rt = getRegister(RT);
        int shamt = Integer.parseInt(SHAMT);

        reg[rd] = reg[rt] << shamt;
    }

    private void slt(String RD, String RS, String RT)
    {
        int rd = getRegister(RD);
        int rs = getRegister(RS);
        int rt = getRegister(RT);

        if (reg[rs] < reg[rt])
            reg[rd] = 1;
        else
            reg[rd] = 0;
    }

    private void jr(String RS)
    {
        int rs = getRegister(RS);
        pc = reg[rs] - 1;
    }

    // I-INSTRUCTIONS
    private void addi(String RT, String RS, String IMM)
    {
        int rt = getRegister(RT);
        int rs = getRegister(RS);
        int imm = Integer.parseInt(IMM);

        reg[rt] = reg[rs] + imm;
    }

    private void beq(String RT, String RS, String LABEL)
    {
        int rt = getRegister(RT);
        int rs = getRegister(RS);

        if (reg[rs] == reg[rt])
            pc = this.instruct.hash.get(LABEL + ":") ;
    }

    private void bne(String RT, String RS, String LABEL)
    {
        int rt = getRegister(RT);
        int rs = getRegister(RS);

        if (reg[rs] != reg[rt])
            pc = this.instruct.hash.get(LABEL + ":");
    }

    private void lw(String RT, String IMM, String RS)
    {
        int rt = getRegister(RT);
        int imm = Integer.parseInt(IMM);
        int rs = getRegister(RS);

        int index = reg[rs] + imm;
        reg[rt] = mem[index];
    }

    private void sw(String RT, String IMM, String RS)
    {
        int rt = getRegister(RT);
        int imm = Integer.parseInt(IMM);
        int rs = getRegister(RS);

        int index = reg[rs] + imm;
        mem[index] = reg[rt];
    }

    // J-INSTRUCTIONS
    private void j(String LABEL)
    {
        int addr = this.instruct.hash.get(LABEL + ":");
        pc = addr - 1;
    }

    private void jal(String LABEL)
    {
        int addr = this.instruct.hash.get(LABEL + ":");
        reg[31] = pc + 1;
        pc = addr - 1;

    }
    public int getRegister(String reg)
    {
        switch (reg){
            case "$zero":
                return 0;
            case "$0":
                return 0;
            case "$v0":
                return 2;
            case "$v1":
                return 3;
            case "$a0":
                return 4;
            case "$a1":
                return 5;
            case "$a2":
                return 6;
            case "$a3":
                return 7;
            case "$t0":
                return 8;
            case "$t1":
                return 9;
            case "$t2":
                return 10;
            case "$t3":
                return 11;
            case "$t4":
                return 12;
            case "$t5":
                return 13;
            case "$t6":
                return 14;
            case "$t7":
                return 15;
            case "$s0":
                return 16;
            case "$s1":
                return 17;
            case "$s2":
                return 18;
            case "$s3":
                return 19;
            case "$s4":
                return 20;
            case "$s5":
                return 21;
            case "$s6":
                return 22;
            case "$s7":
                return 23;
            case "$t8":
                return 24;
            case "$t9":
                return 25;
            case "$sp":
                return 29;
            case "$ra":
                return 31;
        }
        return -1;
    }
}
