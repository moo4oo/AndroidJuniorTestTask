package com.example.dima.androidjuniortesttask;

import android.app.ActionBar;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dima.androidjuniortesttask.Room.CitiesDaoAccess;
import com.example.dima.androidjuniortesttask.Room.CitiesDatabase;
import com.example.dima.androidjuniortesttask.Room.City;

public class EditActivity extends AppCompatActivity {
    EditText cityName;
    Spinner cityType;
    Spinner citySeason;
    EditText firstMonth;
    EditText secondMonth;
    EditText thirdMonth;
    TextView firstM;
    TextView secondM;
    TextView thirdM;
    Button saveBtn;
    Button deleteBtn;
    Button okBtn;
    private int MODE = 0;

    private City city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        saveBtn = findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncSQLIte().execute(0);
            }
        });
        deleteBtn = findViewById(R.id.delete_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncSQLIte().execute(1);
            }
        });
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(!bundle.containsKey("MODE")) {
            setCurrentCity(bundle);
        }else{
            MODE = 1;
            city = new City("","",0f,0f,0f,0f);
            deleteBtn.setClickable(false);
        }
        cityName = findViewById(R.id.city_name);
        cityType = findViewById(R.id.city_type);
        citySeason = findViewById(R.id.season_spinner);
        firstMonth = findViewById(R.id.first_month);
        secondMonth = findViewById(R.id.second_month);
        thirdMonth = findViewById(R.id.third_month);
        firstM = findViewById(R.id.first_month_info);
        secondM = findViewById(R.id.second_month_info);
        thirdM = findViewById(R.id.third_month_info);
        okBtn = findViewById(R.id.save_season_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                message("Данные сезона успешно сохранены");
                InputMethodManager imm =
                        (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        setSpinnerTypeData();
        setSpinnerSeasonData();

        cityName.setText(city.getCityName());


    }

    private class AsyncSQLIte extends AsyncTask<Integer, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            CitiesDatabase db = Room.databaseBuilder(getApplicationContext(), CitiesDatabase.class, "Note").build();
            CitiesDaoAccess dao = db.citiesDaoAccess();

            switch (integers[0]) {
                case 0:
                    saveData();
                    if(MODE == 0) {
                        dao.updateCity(city);
                        message("Данные успешно обновлены");
                    }else{
                        dao.insertCity(city);
                        message("Данные успешно введены");
                    }
                    break;
                case 1:
                    dao.deleteCity(city);
                    break;
            }
            return null;
        }
    }
    private void message(String text){
        Snackbar snackbar = Snackbar
                .make(saveBtn, text, Snackbar.LENGTH_LONG);
        snackbar.show();

    }

    private void saveData() {
        String name = cityName.getText().toString();
        String type = cityType.getSelectedItem().toString();
        String firstTemp = firstMonth.getText().toString();
        String secondTemp = secondMonth.getText().toString();
        String thirdTemp = thirdMonth.getText().toString();
        if (!firstTemp.equals("") && !secondTemp.equals("") && !thirdTemp.equals("") && !name.equals("")) {
            city.setCityName(name);
            city.setCityType(type);
            float temp = (Float.parseFloat(firstTemp) + Float.parseFloat(secondTemp) + Float.parseFloat(thirdTemp))/3;
            switch (citySeason.getSelectedItem().toString()) {
                case "Лето":
                    city.setTempSummer(temp);
                    break;
                case "Зима":
                    city.setTempWinter(temp);
                    break;
                case "Осень":
                    city.setTempAutum(temp);
                    break;
                case "Весна":
                    city.setTempSpring(temp);
                    break;
            }
        }else{
            message("Введите все данные");
        }

    }


    private void setCurrentCity(Bundle bundle) {
        city = new City(bundle.getString("cityType"),
                bundle.getString("cityName"),
                bundle.getFloat("summerTemp"),
                bundle.getFloat("winterTemp"),
                bundle.getFloat("autumnTemp"),
                bundle.getFloat("springTemp"));
        city.setCityId(bundle.getInt("id"));
    }

    private void setSpinnerTypeData() {
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.city_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityType.setAdapter(adapter);
        cityType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city.setCityType(cityType.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        int selection;
        switch (city.getCityType()) {
            case "малый":
                selection = 0;
                break;
            case "средний":
                selection = 1;
                break;
            case "большой":
                selection = 2;
                break;
            default:
                selection = 0;
                break;
        }
        cityType.setSelection(selection);
    }

    private void setSpinnerSeasonData() {
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.city_season, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySeason.setAdapter(adapter);
        citySeason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setSeasonMonths();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        citySeason.setSelection(0);

    }

    private void setSeasonMonths() {
        firstMonth.setText("");
        secondMonth.setText("");
        thirdMonth.setText("");
        switch (citySeason.getSelectedItem().toString()) {
            case "Лето":
                firstM.setText("Июнь");
                secondM.setText("Июль");
                thirdM.setText("Август");
                break;
            case "Зима":
                firstM.setText("Декабрь");
                secondM.setText("Январь");
                thirdM.setText("Февраль");
                break;
            case "Осень":
                firstM.setText("Сентябрь");
                secondM.setText("Октябрь");
                thirdM.setText("Ноябрь");
                break;
            case "Весна":
                firstM.setText("Март");
                secondM.setText("Апрель");
                thirdM.setText("Май");
                break;
        }
    }
}
