import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TratutorCodigoMorseGUI extends JFrame implements KeyListener {
    private CodigoMorseController codigomorseController;

    //criando os campos de area do InputTexto e do codigo morse
    private JTextArea inputTextArea, morseCodeArea;
    public TratutorCodigoMorseGUI(){
        super("Tardutor Morse");

        setSize(new Dimension(540, 700));

        setResizable(false);

        setLayout(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        getContentPane().setBackground(Color.decode("#264653"));

        setLocationRelativeTo(null);

        codigomorseController = new CodigoMorseController();
        addGuiComponente();
    }

    private void addGuiComponente(){

        //Criando titulo
        JLabel titulo = new JLabel("Morse Code translator");
        titulo.setFont(new Font("Dialog" , Font.BOLD, 32));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBounds(0,0,540,100);

        //input de texto
        JLabel inputTexto = new JLabel("Text:");
        inputTexto.setFont(new Font("Dialog",Font.BOLD, 16));
        inputTexto.setForeground(Color.WHITE);
        inputTexto.setBounds(20,100,200,30);

        //Criando area para inser o texto e colocando borda
        inputTextArea = new JTextArea();
        inputTextArea.setFont(new Font("Dialog",Font.PLAIN,18));
        inputTextArea.addKeyListener(this);
        inputTextArea.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        //define a quebra de linhas com relação ao tamanho do componente
        inputTextArea.setLineWrap(true);
        //texto sera quebrado entre palavras
        inputTextArea.setWrapStyleWord(true);

        JScrollPane inputTextScroll = new JScrollPane(inputTextArea);
        inputTextScroll.setBounds(20,132,484,210);

        //input do codigo morse

        JLabel morseCodeInput = new JLabel("Morse Code:");
        morseCodeInput.setFont(new Font("Dialog", Font.BOLD, 16));
        morseCodeInput.setForeground(Color.WHITE);
        morseCodeInput.setBounds(20,360,200,30);

        morseCodeArea = new JTextArea();
        morseCodeArea.setFont(new Font("Dialog",Font.PLAIN,18));
        morseCodeArea.setEditable(false);
        morseCodeArea.setLineWrap(true);
        morseCodeArea.setWrapStyleWord(true);
        morseCodeArea.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JScrollPane morseCodeAreaScroll = new JScrollPane(morseCodeArea);
        morseCodeAreaScroll.setBounds(20,400,484,210);

        JButton playSoundButton = new JButton("Play");
        playSoundButton.setBounds(210,620,100,30);
        playSoundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundButton.setEnabled(false);

                Thread playMoreseCodeThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            String[] morseCodeMessage = morseCodeArea.getText().split(" ");
                            codigomorseController.playSound(morseCodeMessage);
                        }catch (LineUnavailableException lineUnavailableException){
                            lineUnavailableException.printStackTrace();
                        }catch (InterruptedException interruptedException){
                            interruptedException.printStackTrace();
                        }finally {
                            playSoundButton.setEnabled(true);
                        }
                    }
                });
                playMoreseCodeThread.start();
            }
        });

        //adicionando ao GUI
        add(titulo);
        add(inputTexto);
        add(inputTextScroll);
        add(morseCodeInput);
        add(morseCodeAreaScroll);
        add(playSoundButton);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() != KeyEvent.VK_SHIFT){
            String inputText = inputTextArea.getText();

            morseCodeArea.setText(codigomorseController.tratutorMorse(inputText));
        }
    }
}
