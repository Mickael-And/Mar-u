package com.example.maru.activity.dialog_fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Point;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.maru.R;
import com.example.maru.event.DateFilterEvent;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fragment sous forme de popup pour le filtre par date.
 */
public class DateFilterDialogFragment extends DialogFragment {

    @BindView(R.id.date_filter_start_container)
    TextInputLayout startDateFilterContainer;
    @BindView(R.id.edt_date_filter_start)
    TextInputEditText edtStartDate;

    @BindView(R.id.date_filter_end_container)
    TextInputLayout endDateFilterContainer;
    @BindView(R.id.edt_date_filter_end)
    TextInputEditText edtEndDate;

    /**
     * Date et heure de début du filtre.
     */
    private Calendar startDateFilter = Calendar.getInstance();

    /**
     * Date et heure de fin du filtre.
     */
    private Calendar endDateFilter = Calendar.getInstance();


    public DateFilterDialogFragment() {
        // Empty constructor required
    }

    /**
     * Constructeur static.
     *
     * @return instance d'un {@link DateFilterDialogFragment}
     */
    public static DateFilterDialogFragment newInstance() {
        return new DateFilterDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_date_filter, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Désactivation du clavier pour le choix de la date
        this.edtStartDate.setRawInputType(InputType.TYPE_NULL);
        this.edtEndDate.setRawInputType(InputType.TYPE_NULL);
    }

    @Override
    public void onStart() {
        super.onStart();

        this.edtStartDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                this.manageDateField(this.startDateFilterContainer, this.edtStartDate, this.startDateFilter);
            }
        });
        this.edtStartDate.setOnClickListener(v -> this.manageDateField(this.startDateFilterContainer, this.edtStartDate, this.startDateFilter));

        this.edtEndDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                this.manageDateField(this.endDateFilterContainer, this.edtEndDate, this.endDateFilter);
            }
        });
        this.edtEndDate.setOnClickListener(v -> this.manageDateField(this.endDateFilterContainer, this.edtEndDate, this.endDateFilter));
    }

    @Override
    public void onResume() {
        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 80% of the screen width
        window.setLayout((int) (size.x * 0.80), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
        super.onResume();
    }

    /**
     * Gestion d'un champ de saisie de date et heure.
     *
     * @param dateFieldContainer conteneur du champ de date
     * @param edtOfDate          edittext de saisie de la date
     * @param dateToEnter        date à saisir
     */
    private void manageDateField(TextInputLayout dateFieldContainer, TextInputEditText edtOfDate, Calendar dateToEnter) {
        if (getContext() != null) {
            dateFieldContainer.setError(null);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
                dateToEnter.set(year, month, dayOfMonth);
                this.enterTime(edtOfDate, dateToEnter);
            }, dateToEnter.get(Calendar.YEAR), dateToEnter.get(Calendar.MONTH), dateToEnter.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        }
    }

    /**
     * Saisie l'heure grâce à un {@link android.widget.TimePicker}.
     * Met en forme la date et heure à afficher.
     *
     * @param edtOfDate   EditText d'affichage de la date
     * @param dateToEnter Date à saisir
     */
    private void enterTime(TextInputEditText edtOfDate, Calendar dateToEnter) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), (view, hourOfDay, minute) -> {
            dateToEnter.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateToEnter.set(Calendar.MINUTE, minute);

            // Mise en forme de la date pour l'EditText
            edtOfDate.setText(String.format(Locale.getDefault(), "%02d/%02d/%d %02dh%02d", dateToEnter.get(Calendar.DAY_OF_MONTH),
                    dateToEnter.get(Calendar.MONTH) + 1, dateToEnter.get(Calendar.YEAR), dateToEnter.get(Calendar.HOUR_OF_DAY), dateToEnter.get(Calendar.MINUTE)));

        }, dateToEnter.get(Calendar.HOUR_OF_DAY), dateToEnter.get(Calendar.HOUR_OF_DAY), true);
        timePickerDialog.show();
    }

    /**
     * Vérifie la validité des dates et heures de filtres saisies.
     *
     * @return true si valide
     */
    private boolean datesAreValid() {
        boolean areValid;

        if (this.startDateFilterContainer.getError() != null && this.endDateFilterContainer.getError() != null) {
            areValid = false;
        } else if (TextUtils.isEmpty(this.edtStartDate.getText())) {
            areValid = false;
            this.startDateFilterContainer.setError("Choisir une date et heure");
        } else if (TextUtils.isEmpty(this.edtEndDate.getText())) {
            areValid = false;
            this.endDateFilterContainer.setError("Choisir une date et heure");
        } else if (this.startDateFilter.after(this.endDateFilter)) {
            areValid = false;
            this.endDateFilterContainer.setError("Date de fin avant date de début");
        } else {
            areValid = true;
        }
        return areValid;
    }

    @OnClick(R.id.btn_date_filter_ok)
    public void applyFilter() {
        if (this.datesAreValid()) {
            EventBus.getDefault().post(new DateFilterEvent(this.startDateFilter, this.endDateFilter));
            dismiss();
        }
    }

    @OnClick(R.id.btn_date_filter_cancel)
    public void dismissFragment() {
        dismiss();
    }
}

