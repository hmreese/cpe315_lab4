public class pipeline {
    String pipe[];
    int cycles;
    int instructions;
    int pipePC;
    int delay;
    int simStep;
    boolean isEmpty;
    boolean lw;

    public pipeline()
    {
        this.pipe = new String[4];
        this.cycles = 1;
        this.instructions = 1;
        this.pipePC = 0;
        this.delay = 0;
        this.simStep = 0;
        this.lw = false;
        this.isEmpty = false;

        for(int i = 0; i < 4; i++){
            this.pipe[i] = "empty";
        }
    }
}
