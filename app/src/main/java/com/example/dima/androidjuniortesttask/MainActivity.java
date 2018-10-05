package com.example.dima.androidjuniortesttask;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dima.androidjuniortesttask.Decorator.CityTemperature;
import com.example.dima.androidjuniortesttask.Factory.TypeFactory;
import com.example.dima.androidjuniortesttask.Observer.TempObserved;
import com.example.dima.androidjuniortesttask.Observer.TempSubscriber;
import com.example.dima.androidjuniortesttask.Room.CitiesDaoAccess;
import com.example.dima.androidjuniortesttask.Room.CitiesDatabase;
import com.example.dima.androidjuniortesttask.Room.City;
import com.example.dima.androidjuniortesttask.Strategy.CalcCelsiusTemp;
import com.example.dima.androidjuniortesttask.Strategy.CalcFahrenheitTemp;
import com.example.dima.androidjuniortesttask.Strategy.CalcKelvinTemp;
import com.example.dima.androidjuniortesttask.Strategy.CalcTempStrategy;
import com.example.dima.androidjuniortesttask.Strategy.TempCalc;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Spinner citySpinner;
    private Spinner seasonSpinner;
    private TextView tempView;
    private TextView typeView;
    private TempObserved tempObserved;
    private Button fahrenheitBtn;
    private Button celsiusBtn;
    private Button kelvinBtn;
    private ProgressBar progressBar;
    private CalcTempStrategy tempStrategy;
    private ConstraintLayout layout;
    private float curTemp;
    private City currentCity = new City("s", "s", 5f, 0f, 0f, 0f);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        citySpinner = findViewById(R.id.cities_spinner);
        seasonSpinner = findViewById(R.id.season_spinner);
        tempView = findViewById(R.id.season_temp);
        typeView = findViewById(R.id.city_type);
        progressBar = findViewById(R.id.progress_bar);
        layout = findViewById(R.id.constr_layout);
        tempObserved = new TempObserved(tempView);
        tempObserved.addObserver(new TempSubscriber());
        tempStrategy = new CalcTempStrategy();
        tempStrategy.setTempCalc(new CalcCelsiusTemp());
        setStrategyButtons();
        Button editBtn = findViewById(R.id.edit_btn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = (new Intent(MainActivity.this, EditActivity.class));

                intent.putExtra("id", currentCity.getCityId());
                intent.putExtra("cityType", currentCity.getCityType());
                intent.putExtra("cityName", currentCity.getCityName());
                intent.putExtra("autumnTemp", currentCity.getTempAutum());
                intent.putExtra("springTemp", currentCity.getTempSpring());
                intent.putExtra("winterTemp", currentCity.getTempWinter());
                intent.putExtra("summerTemp", currentCity.getTempSummer());
                startActivity(intent);
            }
        });

    }
    private void setVisibility(boolean visible){

    }
    private void updateStrategy(TempCalc tempCalc){
        tempStrategy.setTempCalc(tempCalc);
        tempView.setText(tempStrategy.execute(curTemp));
    }

    private void setStrategyButtons(){
        fahrenheitBtn = findViewById(R.id.fahrenheit_btn);
        fahrenheitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStrategy(new CalcFahrenheitTemp());
            }
        });
        celsiusBtn = findViewById(R.id.celsius_btn);
        celsiusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStrategy(new CalcCelsiusTemp());
            }
        });
        kelvinBtn = findViewById(R.id.kelvin_btn);
        kelvinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStrategy(new CalcKelvinTemp());
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        TypeFactory.smallCities = new ArrayList<>();
        TypeFactory.mediumCities = new ArrayList<>();
        TypeFactory.bigCities = new ArrayList<>();

        for(int i = 0; i < layout.getChildCount(); i++){
            View child = layout.getChildAt(i);
            child.setVisibility(View.INVISIBLE);
        }
        progressBar.setVisibility(View.VISIBLE);

        new CitiesAsyncTask().execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_new_city_item) {
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("MODE", 1);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class CitiesAsyncTask extends AsyncTask<Void, String, ArrayList<City>> {

        @Override
        protected void onPostExecute(ArrayList<City> cities) {
            super.onPostExecute(cities);
            for (City city : cities) {
                if (city.getCityType().equals("малый")) {
                    if (!TypeFactory.smallCities.contains(city.getCityName())) {
                        TypeFactory.smallCities.add(city.getCityName());
                    }
                }else if(city.getCityType().equals("средний")){
                    if(!TypeFactory.mediumCities.contains(city.getCityName())){
                        TypeFactory.mediumCities.add(city.getCityName());
                    }
                }else if(city.getCityType().equals("большой")){
                    if(!TypeFactory.bigCities.contains(city.getCityName())){
                        TypeFactory.bigCities.add(city.getCityName());
                    }
                }
            }
            setCitiesSpinnerData(cities);
            setSeasonSpinnerData(cities);
            for(int i = 0; i < layout.getChildCount(); i++){
                View child = layout.getChildAt(i);
                child.setVisibility(View.VISIBLE);
            }
            progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected ArrayList<City> doInBackground(Void... voids) {
            CitiesDatabase db = Room.databaseBuilder(getApplicationContext(), CitiesDatabase.class, "Note").build();
            List<City> cities;
            CitiesDaoAccess dao = db.citiesDaoAccess();
            cities = dao.getAllCities();
            if (cities != null && cities.size() != 0) {
                return new ArrayList<>(cities);
            } else {
                /*
                for (int i = 0; i < 10; i++) {
                    City city = new City("малый", "Екатеринбург" + i, i + 0.1f, i + 0.2f, i + 0.3f, i + 0.4f);
                    dao.insertCity(city);
                }*/
                insertPrepopulateData(dao);
                cities = dao.getAllCities();
                return new ArrayList<>(cities);
            }
        }
    }
    private void insertPrepopulateData(CitiesDaoAccess dao){
        City city1 = new City("большой", "Екатеринбург", 15f, 13.2f, 21.1f, 7f);
        dao.insertCity(city1);
        City city2 = new City("малый", "Омск", 14f, 0.5f, 2f, 7f);
        dao.insertCity(city2);
        City city3 = new City("средний", "Пермь", 10f, 5f, 4f, 3f);
        dao.insertCity(city3);
        City city4 = new City("большой", "Москва", 0f, -12f, -4f, 6f);
        dao.insertCity(city4);
    }

    private void setCitiesSpinnerData(final ArrayList<City> cities) {
        final TextView textView = findViewById(R.id.season_temp);
        ArrayList<String> citiesNames = new ArrayList<>();
        for (int i = 0; i < cities.size(); i++) {
            citiesNames.add(cities.get(i).getCityName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, citiesNames);
        citySpinner.setAdapter(adapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeView.setText(TypeFactory.getType(citySpinner.getSelectedItem().toString()).getType());
                if(seasonSpinner.getSelectedItemPosition() != 0) {
                    seasonSpinner.setSelection(0);
                }else{
                    updateSeasonData(cities.get(position));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        citySpinner.setSelection(0);

    }
    private void updateSeasonData(City city){
        currentCity.setCityId(city.getCityId());
        currentCity.setCityName(city.getCityName());
        currentCity.setCityType(city.getCityType());
        currentCity.setTempAutum(city.getTempAutum());
        currentCity.setTempSpring(city.getTempSpring());
        currentCity.setTempSummer(city.getTempSummer());
        currentCity.setTempWinter(city.getTempWinter());
        switch (seasonSpinner.getSelectedItem().toString()) {
            case "Лето":
                setSeasonText(new CityTemperature(city.getTempSummer()));
                break;
            case "Осень":
                setSeasonText(new CityTemperature(city.getTempAutum()));
                break;
            case "Зима":
                setSeasonText(new CityTemperature(city.getTempWinter()));
                break;
            case "Весна":
                setSeasonText(new CityTemperature(city.getTempSpring()));
                break;

        }

    }
    private void setSeasonText(CityTemperature temp){
        curTemp = temp.getTemp();
        tempView.setText(tempStrategy.execute(temp.getTemp()));
        tempObserved.updateTemp(temp.getTemp());

    }

    private void setSeasonSpinnerData(final ArrayList<City> cities) {
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.city_season, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seasonSpinner.setAdapter(adapter);
        seasonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                City city = cities.get(citySpinner.getSelectedItemPosition());
                updateSeasonData(city);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        seasonSpinner.setSelection(0);
    }
}
