package com.ana.handlers;

import com.ana.models.Button;
import com.ana.models.Section;

import java.util.ArrayList;

/**
 * Created by Admin on 25-07-2017.
 */

public interface ViewModelCallback {

    void onSectionAdded(Section mSection);

    void onSectionRenderCompletion(Section mSection);

    void setPrefix(Button mButton);

    void setPostfix(Button mButton);

    void showInputText(Button mButton, int inputType);

    void clearButtons();

    void addButtonList(ArrayList<Button> buttonArrayList);

    void performAction();
}
