package br.com.cursojava.petshop.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class UploadUtil {
    public static boolean fazerUploadImage(MultipartFile image){
        if(!image.isEmpty()){
            String nomeArquivo = image.getOriginalFilename();
            try {
                //criando o diretorio
                String pastaUploadImage = "C:\\Users\\luisp\\Documents\\Programacao\\spring\\sorveteria\\src\\main\\resources\\static\\imagens\\img-uploads";
                File dir = new File(pastaUploadImage);
                if (!dir.exists()){
                    dir.mkdir();
                }
                //criando o arquivo no diretorio
                File serverFile = new File(dir.getAbsoluteFile() + File.separator + nomeArquivo);

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));

                stream.write(image.getBytes());
                stream.close();

                System.out.println("Armazenado em : "+ serverFile.getAbsolutePath());
                System.out.println("voc fez upload do arquivo "+ nomeArquivo + " com sucesso" );
            }catch (Exception e){
                System.out.println("voce falhou em carregar com o arquivo" + nomeArquivo + " => " +e.getMessage());
            }
        }else {
            System.out.println(" voce falhou em carregar o arquivo ");
        }
return true;
    }
}
