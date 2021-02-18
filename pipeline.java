public class pipeline {
    String pipe[];
    int cycles;
    int instructions;
    int pipePC;
    int delay;
    boolean isEmpty;

    public pipeline()
    {
        this.pipe = new String[4];
        this.cycles = 0;
        this.instructions = 0;
        this.pipePC = 0;
        this.delay = 0;
        this.isEmpty = false;

        for(int i = 0; i < 4; i++){
            this.pipe[i] = "empty";
        }
    }
}
