package com.example.android.sample.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class AnotherCalcActivity extends Activity implements TextWatcher, View.OnClickListener{
    //上のEditText
    private EditText numberInput1;
    //下のEditText
    private EditText numberInput2;
    //演算子選択用のSpinner
    private Spinner operatorSelector;
    //計算結果表示用のTextView
    private TextView calcResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_calc);

        //上のEditText
        numberInput1 = (EditText)findViewById(R.id.numberInput1);
        //上のEditTextの文字イベントを受け取る。
        numberInput1.addTextChangedListener(this);

        //下のEditText
        numberInput2 = (EditText)findViewById(R.id.numberInput2);
        //下のEditTextの文字イベントを受け取る。
        numberInput2.addTextChangedListener(this);

        //演算子選択用のSpinner
        operatorSelector = (Spinner)findViewById(R.id.operatorSelector);

        //計算結果表示用のTextView
        calcResult = (TextView)findViewById(R.id.calcResult);

        //「戻るボタン」
        findViewById(R.id.backButton).setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        //タップされた時の処理を実装する。
        //タップされたViewのIDを取得する。
        int id = v.getId();
        //どちらかのEditTextに値が入っていない場合
        if (!checkEditTextInput()) {
            //キャンセルとみなす
            setResult(RESULT_CANCELED);
        } else {
            //計算結果
            int result = calc();

            //インテントを生成し、計算結果を戻す。
            Intent data = new Intent();
            data.putExtra("result", result);
            setResult(RESULT_OK,data);
        }
        //アクティビティーの終了
        finish();
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //テキストが変更される直前に呼ばれる。sは変更前の内容

    }
    @Override
    public void onTextChanged(CharSequence s,int start, int before, int count) {
        //テキストが変更されるときに呼ばれる。sは変更後の内容で編集不可
    }
    @Override
    public void afterTextChanged(Editable s) {
        //テキストが変更された後に呼ばれる。sは変更後の内容で編集可能
        refreshResult();
    }
    //計算結果の表示を更新する。
    private void refreshResult() {
        if(checkEditTextInput()) {
            //計算を行う。
            int result = calc();

            //計算結果用のTextViewを書き換える。
            String resultText = getString(R.string.calc_result_text, result);
            calcResult.setText(resultText);
        } else {
            //どちらか入力されていない状態の場合、計算結果用の表示をデフォルトに戻す。
            calcResult.setText(R.string.calc_result_default);
        }
    }
    //計算を行う。
    private int calc() {
        //入力内容を取得する
        String input1 = numberInput1.getText().toString();
        String input2 = numberInput2.getText().toString();

        //int型に変換する。
        int number1 = Integer.parseInt(input1);
        int number2 = Integer.parseInt(input2);

        //Spinerから、選択中のindexを取得する。
        int operator = operatorSelector.getSelectedItemPosition();

        //indexに応じて計算結果を返す。
        switch(operator) {
            case 0: //足し算
                return number1 + number2;
            case 1: //引き算
                return number1 - number2;
            case 2: //掛け算
                return number1 * number2;
            case 3: //割り算
                return number1 / number2;
            default:
                //通常発生しない。
                throw new RuntimeException();
        }
    }

    //２つのEditTextに入力がされているかチェックする。
    private boolean checkEditTextInput() {
        //入力内容を取得する。
        String input1 = numberInput1.getText().toString();
        String input2 = numberInput2.getText().toString();
        //２つとも空文字（あるいはnull)でなければ、true
        return !TextUtils.isEmpty(input1) && !TextUtils.isEmpty(input2);
    }

}
