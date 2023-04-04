import java.util.StringTokenizer;

public class CommandLine{ 
    public String command = "";
    public String argu1 = null;
    public String argu2 = null;
    public String argu3 = null;
    public String argu4 = null;
    public int tokenCount = 0;
    
    public CommandLine(String input) {
        StringTokenizer inputs=new StringTokenizer(input, " ");
        tokenCount = inputs.countTokens();

        if(tokenCount > 0){
            command = inputs.nextToken();
            if(tokenCount>1){
                argu1 = inputs.nextToken();
                if(tokenCount>2){
                    argu2 = inputs.nextToken();
                    if(tokenCount>3){
                        argu3 = inputs.nextToken();
                        if(tokenCount>4){
                            argu4 = inputs.nextToken();
                        }
                    }
                }
            }
        }
        
    }  
}
    
