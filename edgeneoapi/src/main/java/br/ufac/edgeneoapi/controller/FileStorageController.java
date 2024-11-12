// package br.ufac.edgeneoapi.controller;

// import java.io.IOException;
// import java.net.MalformedURLException;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.util.List;
// import java.util.stream.Collectors;

// import org.springframework.core.io.UrlResource;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Controller;
// import org.springframework.util.StringUtils;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.multipart.MultipartFile;
// import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

// import br.ufac.edgeneoapi.config.FileStorageProperties;
// import jakarta.servlet.http.HttpServletRequest;

// @Controller
// @RequestMapping("/api/files")
// public class FileStorageController {

//     private final Path fileStorageLocation;

//     public FileStorageController(FileStorageProperties fileStorageProperties) {
//         this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
//                 .toAbsolutePath().normalize();
//     }

//     @PostMapping("/upload")
//     public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
//         String fileName = StringUtils.cleanPath(file.getOriginalFilename());

//         try {
//             if (fileName.contains("..")) {
//                 return ResponseEntity.badRequest().body("Invalid file path.");
//             }

//             Path targetLocation = fileStorageLocation.resolve(fileName);
//             file.transferTo(targetLocation);

//             String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                 .path("/api/files/download/")
//                 .path(fileName)
//                 .toUriString();

//             return ResponseEntity.ok("File uploaded successfully! Download link: " + fileDownloadUri);

//         } catch (IOException ex) {
//             return ResponseEntity.badRequest().body("Could not upload the file: " + ex.getMessage());
//         }
//     }

//     @GetMapping("/download/{fileName:.+}")
//     public ResponseEntity<org.springframework.core.io.Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
//         Path filePath = fileStorageLocation.resolve(fileName).normalize();
        
//         try {
//             UrlResource resource = new UrlResource(filePath.toUri());
//             if (!resource.exists()) {
//                 return ResponseEntity.notFound().build();
//             }

//             // Obtém o tipo de conteúdo (MIME type)
//             String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//             if (contentType == null) {
//                 contentType = "application/octet-stream";
//             }

//             return ResponseEntity.ok()
//                 .contentType(MediaType.parseMediaType(contentType))
//                 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                 .body(resource);
//         } catch (MalformedURLException e) {
//             return ResponseEntity.notFound().build();
//         } catch (IOException e) {
//             return ResponseEntity.status(500).build();
//         }
//     }

//     @GetMapping("/list")
//     public ResponseEntity<List<String>> listFiles() throws IOException {
//         List<String> fileNames = Files.list(fileStorageLocation)
//             .map(Path::getFileName)
//             .map(Path::toString)
//             .collect(Collectors.toList());

//         return ResponseEntity.ok(fileNames);
//     }
// }
