package find_stronghold;

import java.awt.*;
import java.awt.event.*;

public class MainWindow
{
    private final Frame frame = new Frame("終界祭壇尋找器");
    private final TextField[] firstThrowTextFields = new TextField[2]; //第一組丟出的座標
    private final TextField[] firstLandTextFields = new TextField[2]; //第一組落地的座標
    private final TextField[] secondThrowTextFields = new TextField[2]; //第二組丟出的座標
    private final TextField[] secondLandTextFields = new TextField[2]; //第二組落地的座標
    private final TextField[][] allTextFields = { firstThrowTextFields,firstLandTextFields,secondThrowTextFields,secondLandTextFields };
    private final Font font = new Font("Serif", Font.PLAIN, 18); //字型
    private final LabelWithFont resultLabel = new LabelWithFont("可能位置: ", new Font("Serif", Font.PLAIN, 24));
    private final String[][] coordinates = new String[4][2];
    private final Calculate calculate = new Calculate();

    public MainWindow()
    {
        initialTextFields();

        frame.setSize(480, 340);
        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent windowEvent) //當關閉視窗的時候
            {
                frame.dispose(); //結束
            }
        });

        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("icon.png")));
        frame.setLayout(new BorderLayout());
        frame.add(new Label(""), BorderLayout.WEST);
        frame.add(new Label(""), BorderLayout.SOUTH);
        frame.add(new Label(""), BorderLayout.NORTH);
        Panel centerPanel = new Panel(new GridLayout(5, 1));
        frame.add(centerPanel, BorderLayout.CENTER);

        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 15, 1);
        Panel firstThrowPanel = new Panel(flowLayout);
        firstThrowPanel.add(new LabelWithFont("請輸入第一組起始座標", font));
        firstThrowPanel.add(firstThrowTextFields[0]);
        firstThrowPanel.add(firstThrowTextFields[1]);
        centerPanel.add(firstThrowPanel);

        Panel firstLandPanel = new Panel(flowLayout);
        firstLandPanel.add(new LabelWithFont("請輸入第一組落地座標", font));
        firstLandPanel.add(firstLandTextFields[0]);
        firstLandPanel.add(firstLandTextFields[1]);
        centerPanel.add(firstLandPanel);

        Panel secondThrowPanel = new Panel(flowLayout);
        secondThrowPanel.add(new LabelWithFont("請輸入第二組起始座標", font));
        secondThrowPanel.add(secondThrowTextFields[0]);
        secondThrowPanel.add(secondThrowTextFields[1]);
        centerPanel.add(secondThrowPanel);

        Panel secondLandPanel = new Panel(flowLayout);
        secondLandPanel.add(new LabelWithFont("請輸入第二組落地座標", font));
        secondLandPanel.add(secondLandTextFields[0]);
        secondLandPanel.add(secondLandTextFields[1]);
        centerPanel.add(secondLandPanel);

        centerPanel.add(resultLabel);

        frame.setVisible(true);
    }

    private void initialTextFields()
    {
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 2; j++)
            {
                allTextFields[i][j] = new TextField(6);
                allTextFields[i][j].setFont(font);

                int row = i;
                int eitherXOrZ = j;
                allTextFields[i][j].addTextListener(e ->
                {
                    //走訪全部的輸入框
                    for (int m = 0; m < 4; m++)
                    {
                        for (int n = 0; n < 2; n++)
                        {
                            coordinates[m][n] = allTextFields[m][n].getText();
                            if (coordinates[m][n].isEmpty()) //還有空的輸入框
                            {
                                calculate.setStartCalculate(false);
                                //當startCalculate為false時 呼叫findStronghold方法不做計算
                                //只做將字串儲存進陣列的動作 以及過濾非數字的情形
                                resultLabel.setText("可能位置: " + calculate.findStronghold(coordinates[row][eitherXOrZ], row, eitherXOrZ));
                                return;
                            }
                        }
                    }

                    calculate.setStartCalculate(true);
                    resultLabel.setText("可能位置: " + calculate.findStronghold(coordinates[row][eitherXOrZ], row, eitherXOrZ));
                });
            }
        }
    }
}

class LabelWithFont extends Label
{
    public LabelWithFont(String text, Font font)
    {
        super(text, LEFT);
        setFont(font);
    }
}