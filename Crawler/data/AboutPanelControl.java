151
https://raw.githubusercontent.com/fordes123/Subtitles-View/master/src/main/java/org/fordes/subview/controller/AboutPanelControl.java
package org.fordes.subview.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.fordes.subview.main.Launcher;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutPanelControl implements Initializable {
    @FXML
    public ToggleGroup ListGroup;
    @FXML
    private GridPane About,updatePanel,PactPanel,InfoPanel;
    @FXML
    private ToggleButton Info,Pact,UpdateLog;
    @FXML
    private GridPane info_Content;
    @FXML
    private Label InfoPanel_side_titles;
    @FXML
    private TextArea info_TextArea;

    private Object LightTheme=getClass().getClassLoader().getResource("css/mainStyle_Light.css").toString();
    private Object DarkTheme=getClass().getClassLoader().getResource("css/mainStyle_Dark.css").toString();
    private startControl controller= (startControl) Launcher.controllers.get(startControl.class.getSimpleName());

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        About.getStylesheets().add(LightTheme.toString());
    }

    /**
     * 切换颜色模式
     * @param ModeState
     */
    public void modeChange(Boolean ModeState){
        /*移除所有样式表*/
        About.getStylesheets().remove(LightTheme);
        About.getStylesheets().remove(DarkTheme);
        if(ModeState)
            About.getStylesheets().add(LightTheme.toString());
        else
            About.getStylesheets().add(DarkTheme.toString());
    }
    

    private void hide(){
        updatePanel.setVisible(false);
        PactPanel.setVisible(false);
        InfoPanel.setVisible(false);
    }


    public void onInfo(ActionEvent actionEvent) {
        ListGroup.selectToggle(Info);
        Info.setSelected(true);

        if(controller.focus_indicator.equals(Info.getId()))
            return;
        controller.focus_indicator=Info.getId();
        hide();
        InfoPanel.setVisible(true);
    }


    public void onPact(ActionEvent actionEvent) {
        ListGroup.selectToggle(Pact);
        Pact.setSelected(true);

        if(controller.focus_indicator.equals(Pact.getId()))
            return;
        controller.focus_indicator=Pact.getId();
        hide();
        PactPanel.setVisible(true);
    }


    public void onUpdateLog(ActionEvent actionEvent) {
        ListGroup.selectToggle(UpdateLog);
        UpdateLog.setSelected(true);

        if(controller.focus_indicator.equals(UpdateLog.getId()))
            return;
        controller.focus_indicator=UpdateLog.getId();
        hide();
        updatePanel.setVisible(true);
    }



    public void onDeveloperInfo(MouseEvent mouseEvent) {
        InfoPanel_side_titles.setVisible(false);
        info_TextArea.setVisible(false);
        if(!info_Content.isVisible()){
            info_Content.setVisible(true);
        }else
            info_Content.setVisible(false);
    }


    public void onProjectHomepage(MouseEvent mouseEvent) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://github.com/fordes123/Subtitles-Tool"));
    }


    public void onFeedback(MouseEvent mouseEvent) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://fordes.top"));
    }


    public void onProtocol(MouseEvent mouseEvent) {
        /*info_Content.setVisible(false);
        InfoPanel_side_titles.setText("开源协议");
        InfoPanel_side_titles.setVisible(true);*/

    }


    public void onPrivacyStatement(MouseEvent mouseEvent) {
        info_Content.setVisible(false);
        InfoPanel_side_titles.setText("隐私声明");
        info_TextArea.setText("该软件是\"按原样\"提供的,没有任何形式的明示或\n" +
                "暗示,包括但不限于为特定目的和不侵权的适销性\n" +
                "和适用性的保证担保。在任何凊况下,作者或版权\n" +
                "持有人,都无权要求任何索赔,或有关损害赔偿的\n" +
                "其他责任。无论在本软件的使用上或其他买卖交易\n" +
                "中,是否涉及合同,侵权或其他行为。");
        if(!info_TextArea.isVisible()){
            InfoPanel_side_titles.setVisible(true);
            info_TextArea.setVisible(true);
        }
        else{
            InfoPanel_side_titles.setVisible(false);
            info_TextArea.setVisible(false);
        }

    }
}
