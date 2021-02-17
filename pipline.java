public class pipline {
    String pipe[];
    int cycles;

    public pipline(){
        this.pipe = new String[4];
        this.cycles = 0;
        for(int i = 0; i < 4; i++){
            this.pipe[i] = "empty";
        }
    }
}
