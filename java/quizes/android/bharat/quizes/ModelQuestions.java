package quizes.android.bharat.quizes;

public class ModelQuestions {
String ques,correct_ans;
String[] options=new String[4],incorrect_ans=new String[3];
public void setQuestion(String Question){ques=Question;}
public void setCorrect_ans(String Correct){correct_ans=Correct;}
public void setIncorrect_ans(String[] In_correct){ for(int i=0;i<3;i++){ incorrect_ans[i]=In_correct[i]; } }
public String getQuestion(){return ques;}
public String[] getOptions(){
    for(int i=0;i<4;i++){
        if(i<3){options[i]=incorrect_ans[i];}else {options[i]=correct_ans;}
    }
    return options;
}
public String getCorrect_ans(){return correct_ans;}
public String[] getIncorrect_ans(){return incorrect_ans;}
}
