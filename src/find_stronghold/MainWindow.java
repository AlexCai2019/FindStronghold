package find_stronghold;

import java.awt.*;
import java.awt.event.*;

public class MainWindow
{
    private final Frame frame;
    private final TextField[] firstThrowTextFields = new TextField[2]; //第一組丟出的座標
    private final TextField[] firstLandTextFields = new TextField[2]; //第一組落地的座標
    private final TextField[] secondThrowTextFields = new TextField[2]; //第二組丟出的座標
    private final TextField[] secondLandTextFields = new TextField[2]; //第二組落地的座標
    private final TextField[][] allTextFields = { firstThrowTextFields,firstLandTextFields,secondThrowTextFields,secondLandTextFields };
    private final Font font = new Font("Serif", Font.PLAIN, 24); //字型
    private final MyLabel resultLabel = new MyLabel("可能位置: ", new Font("Serif", Font.PLAIN, 36));
    private final String[][] coordinates = new String[4][2];
    private final Calculate calculate = new Calculate();

    public MainWindow()
    {
        initialTextFields();

        frame = new Frame("終界祭壇尋找器");
        frame.setSize(740, 480);
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
        frame.add(new Label(""), BorderLayout.NORTH);
        frame.add(new Label(""), BorderLayout.WEST);
        Panel centerPanel = new Panel(new GridLayout(6, 1));
        frame.add(centerPanel, BorderLayout.CENTER);

        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 15, 1);
        Panel firstThrowPanel = new Panel(flowLayout);
        firstThrowPanel.add(new MyLabel("請輸入第一組起始座標", font));
        firstThrowPanel.add(firstThrowTextFields[0]);
        firstThrowPanel.add(firstThrowTextFields[1]);
        centerPanel.add(firstThrowPanel);

        Panel firstLandPanel = new Panel(flowLayout);
        firstLandPanel.add(new MyLabel("請輸入第一組落地座標", font));
        firstLandPanel.add(firstLandTextFields[0]);
        firstLandPanel.add(firstLandTextFields[1]);
        centerPanel.add(firstLandPanel);

        Panel secondThrowPanel = new Panel(flowLayout);
        secondThrowPanel.add(new MyLabel("請輸入第二組起始座標", font));
        secondThrowPanel.add(secondThrowTextFields[0]);
        secondThrowPanel.add(secondThrowTextFields[1]);
        centerPanel.add(secondThrowPanel);

        Panel secondLandPanel = new Panel(flowLayout);
        secondLandPanel.add(new MyLabel("請輸入第二組落地座標", font));
        secondLandPanel.add(secondLandTextFields[0]);
        secondLandPanel.add(secondLandTextFields[1]);
        centerPanel.add(secondLandPanel);

        centerPanel.add(resultLabel);

        frame.setVisible(true);
    }

    private void initialTextFields()
    {
        TextListener listener = e -> //輸入文字 textValueChanged(TextEvent e)
        {
            for (int i = 0; i < 4; i++)
            {
                for (int j = 0; j < 2; j++)
                {
                    coordinates[i][j] = allTextFields[i][j].getText();
                    if (coordinates[i][j].isEmpty()) //還有空的輸入框
                        return;
                }
            }

            resultLabel.setText("可能位置: " + calculate.findStronghold(coordinates));
        };

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 2; j++)
            {
                allTextFields[i][j] = new TextField(6);
                allTextFields[i][j].setFont(font);
                allTextFields[i][j].addTextListener(listener);
            }
        }
    }
}

class MyLabel extends Label
{
    public MyLabel(String text, Font font)
    {
        super(text, LEFT);
        setFont(font);
    }
}