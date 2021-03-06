package MorfologiaMatematica;

 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author ionildo
 */
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import static java.awt.image.ImageObserver.ALLBITS;
import javax.swing.filechooser.*;

import com.apmcommunity.compuware.AWTAgent;

public class Transformacoes extends javax.swing.JFrame {
     BufferedImage imagem1;
     int flag=0;

    public Transformacoes() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
	
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        
	jMenu2 = new javax.swing.JMenu();
	jMenuItem13 = new javax.swing.JMenuItem();
	jMenuItem14 = new javax.swing.JMenuItem();
	jMenuItem15 = new javax.swing.JMenuItem();


        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu1.setText("File");

        jMenuItem1.setLabel("Open...");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setLabel("Save...");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setLabel("Exit");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

	jMenu2.setText("Operações aritméticas");
	
	jMenuItem13.setLabel("Rotação a Esquerda");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem13);
	
	jMenuItem14.setLabel("Rotação a direita");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem14);
	
	jMenuItem15.setLabel("Subtração de 2 imagens");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem15);

        jMenuBar1.add(jMenu2);


        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 400, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 279, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Open file
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, PNG & GIF Images", "jpg", "png", "gif");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Abrir Imagem");
        int op = chooser.showOpenDialog(this);
        if(op == JFileChooser.APPROVE_OPTION){
            File arq = chooser.getSelectedFile();
            String path = arq.toString();
            try {
                  //carrega nova imagem
                  imagem1 = ImageIO.read(new File(path));
                  System.out.println("Arquivo aberto com sucesso!");
                  ImageIcon icon = new ImageIcon(imagem1);
                  if (flag==0) {
                      jLabel1.setIcon(icon);
                      Container contentPane = getContentPane();
                      contentPane.setLayout(new GridLayout());
                      contentPane.add(new JScrollPane(jLabel1));
                      flag=1;
                  }
                  else jLabel1.setIcon(icon);
                  setSize(imagem1.getWidth()+25, imagem1.getHeight()+70);
            }
            catch(IOException e){
                System.out.println("Erro IO Exception! Verifique se o arquivo especificado existe e tente novamente.");
            }
            catch(Exception e){
                System.out.println("Erro Exception! " + e.getMessage());
            }
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    // Save File
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG Images", "jpg");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Salvar Imagem");
        int op = chooser.showSaveDialog(this);
        if(op == JFileChooser.APPROVE_OPTION){
            File arq = chooser.getSelectedFile();
            String path = arq.toString();
            try {
                ImageIO.write(imagem1,"jpg",new File(path));
                System.out.println("Arquivo salvo com sucesso!");
                }
                catch(IOException e){
                        System.out.println("Erro IO Exception! Verifique se o arquivo especificado existe e tente novamente.");
                }
                catch(Exception e){
                        System.out.println("Erro Exception! " + e.getMessage());
                }
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    // Exit
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        System.exit(1);
    }//GEN-LAST:event_jMenuItem3ActionPerformed
    
    // Rotacao esquerda
    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {
        int width = imagem1.getWidth();
        int height = imagem1.getHeight();
	
	// create new image with the width of height and height of width
	BufferedImage imagemEsquerda = new BufferedImage(imagem1.getHeight(),imagem1.getWidth(),BufferedImage.TYPE_INT_RGB);
	
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
		// get the pixel at the rotated position
		int rgb = imagem1.getRGB(x, (height - 1 - y));
		
		int w = width - 1 - x;
		int h = height - 1 - y;
		// set the width as height and vice-versa
                imagemEsquerda.setRGB(abs(h), abs(w), rgb);
		
            }
        }
	imagem1 = imagemEsquerda;
	ImageIcon icon = new ImageIcon(imagem1);
	jLabel1.setIcon(icon);

        this.setSize(imagem1.getWidth()+75, imagem1.getHeight()+75);
	//this.imageUpdate(imagem1, ALLBITS, 0, 0, width, height);
	System.out.println("Terminou rotação a esquerda!!!");
    }//GEN-LAST:event_jMenuItem13ActionPerformed
    
    // Rotacao direita
    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {
        int width = imagem1.getWidth();
        int height = imagem1.getHeight();
	
	// create new image with the width of height and height of width
	BufferedImage imagemDireita = new BufferedImage(imagem1.getHeight(),imagem1.getWidth(),BufferedImage.TYPE_INT_RGB);
	
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
		// get the pixel at the rotated position
		int rgb = imagem1.getRGB((width - 1 - x), y);

		int w = width - 1 - x;
		int h = height - 1 - y;
		// set the width as height and vice-versa
                imagemDireita.setRGB(abs(h), abs(w), rgb);
		
            }
        }
	imagem1 = imagemDireita;
	ImageIcon icon = new ImageIcon(imagem1);
	jLabel1.setIcon(icon);

        this.setSize(imagem1.getWidth()+75, imagem1.getHeight()+75);
	//this.imageUpdate(imagem1, ALLBITS, 0, 0, width, height);
	System.out.println("Terminou rotação a esquerda!!!");
    }//GEN-LAST:event_jMenuItem13ActionPerformed
    
    // Subtracao
    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {
	
	
	// seleciona para abrir 2 imagens
        JFileChooser chooser = new JFileChooser();
	chooser.setMultiSelectionEnabled(true);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, PNG & GIF Images", "jpg", "png", "gif");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Abrir 2 Imagens");
        int op = chooser.showOpenDialog(this);
	
        if(op == JFileChooser.APPROVE_OPTION){
            File[] arqs = chooser.getSelectedFiles();
            if (arqs.length == 2){
		String path1 = arqs[0].toString();
		String path2 = arqs[1].toString();
		
		BufferedImage imagem2, imagemSubtraida;
	    
		try {
		      //carrega nova imagem
		      imagem1 = ImageIO.read(new File(path1));
		      imagem2 = ImageIO.read(new File(path2));
		      System.out.println("Arquivos aberto com sucesso!");
		      
			// images must have the same size
			int width = imagem1.getWidth();
			int height = imagem1.getHeight();

			imagemSubtraida = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);

			for (int y = 0; y < height; y++) {
			    for (int x = 0; x < width; x++) {
				int rgb1 = imagem1.getRGB(x, y);
				int rgb2 = imagem2.getRGB(x, y);

				int rgbSub = abs(rgb1 - rgb2);

				imagemSubtraida.setRGB(x, y, rgbSub);

			    }
			}

			imagem1 = imagemSubtraida;
			ImageIcon icon = new ImageIcon(imagem1);
			jLabel1.setIcon(icon);

			this.setSize(imagem1.getWidth()+75, imagem1.getHeight()+75);
			//this.imageUpdate(imagem1, ALLBITS, 0, 0, width, height);
			System.out.println("Terminou subtracao!!!");
		}
		catch(IOException e){
		    System.out.println("Erro IO Exception! Verifique se o arquivo especificado existe e tente novamente.");
		}
		catch(Exception e){
		    System.out.println("Erro Exception! " + e.getMessage());
		}
	    }
	    else
		System.out.println("Selecione 2 imagens!");
        }
    }//GEN-LAST:event_jMenuItem15ActionPerformed


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        // ADK ///////////////////////////////////////////////////////////////
        AWTAgent.initialize();
        // ADK ///////////////////////////////////////////////////////////////
    	
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Transformacoes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Transformacoes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Transformacoes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Transformacoes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Transformacoes().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    // End of variables declaration//GEN-END:variables

    private int abs(int number) {
	return (number < 0) ? -number : number;
    }
}
