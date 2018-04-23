package quizes.android.bharat.quizes.data;

import android.provider.BaseColumns;

public class User_contract {
    private User_contract(){}

    public static final class Quiz_entry implements BaseColumns{
        public static final String Table_Name="Quiz_Data";

        public static final String Id=BaseColumns._ID;
        public static final String Column_Quiz_Question_Category="Question_Category";
        public static final String Column_Quiz_Question="Questions";
        public static final String Column_Quiz_Correct_Answer="Correct_Answer";
        public static final String Column_Quiz_Incorrect_First="Incorrect_First";
        public static final String Column_Quiz_Incorrect_Second="Incorrect_Second";
        public static final String Column_Quiz_Incorrect_Third="Incorrect_Third";
    }

   public static final class User_entry{
        public static final String Table_Name="User_Data";

        public static final String Column_User_Name="User_Name";
        public static final String Column_User_Number="User_Number";
    }
}
