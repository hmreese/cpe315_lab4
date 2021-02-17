public class pipeline {
    String pipe[];
    int cycles;
    int instructions;

    public pipeline(){
        this.pipe = new String[4];
        this.cycles = 1;
        this.instructions = 1;
        for(int i = 0; i < 4; i++){
            this.pipe[i] = "empty";
        }
    }
}
