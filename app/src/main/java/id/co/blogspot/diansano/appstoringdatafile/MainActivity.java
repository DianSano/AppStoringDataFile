package id.co.blogspot.diansano.appstoringdatafile;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    EditText textBox;
    final int READ_BLOCK_SIZE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textBox = findViewById(R.id.editText);
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public void onClickSave(View v)      {
        if (isExternalStorageWritable()) {
            String str = textBox.getText().toString();
            try {
                File sdCard = getBaseContext().getExternalCacheDir();
                File directory = new File(sdCard.getAbsolutePath() + "/eksternal");
                directory.mkdirs();
                File file = new File(directory, "test.txt");
                FileOutputStream fOut = new FileOutputStream(file);
                OutputStreamWriter osw = new OutputStreamWriter(fOut);
                //write the string to the file
                osw.write(str);
                osw.flush();
                osw.close();
                //display the saved message
                Toast.makeText(this, "File saved successfully!", Toast.LENGTH_SHORT).show();
                //clear the EditText
                textBox.setText("");
            } catch (IOException ioe) {
                Toast.makeText(this, "File can't be saved!", Toast.LENGTH_SHORT).show();
                ioe.printStackTrace();
            }
        }
    }

    public void onClickLoad(View v) {
        try {
            //SDCard
            File sdCard = getBaseContext().getExternalCacheDir();
            File direcetory = new File(sdCard.getAbsolutePath() + "/eksternal");
            File file = new File(direcetory, "test.txt");
            FileInputStream fIn = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fIn);
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String s = "";
            int charRead;
            while ((charRead = isr.read(inputBuffer)) > 0) {
                //convert chars to a string
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                s += readString;
                inputBuffer = new char[READ_BLOCK_SIZE];
            }
            textBox.setText(s);
            Toast.makeText(this, "File loaded successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "File can't be loaded!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }
    }
}
