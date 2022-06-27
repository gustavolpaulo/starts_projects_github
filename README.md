# starts_projects_github

O app tem o obejtivo de listar os projetos em Kotlin com mais estrelas. 

   A request para API do GitHub é realizada no init do app o qual logo em seguida realiza o armazenamento das informações dos repositorios e do seu respectivo autor, assim como sua foto de perfil, em cache usando Room.
   
   Para o acesso as informações atráves da API e Room foi utilizado o RxJava, o qual possibilitou a comunicação de forma assíncrona.
   
   Quanto ao carregamento da lista, esta é realizada em etapas, similar a navegação pela web por páginas em uma consulta, por meio de um scroll infinito
 
 <img src="https://user-images.githubusercontent.com/52495204/176041001-31540f3d-d782-459f-9772-e3606e2c8ba6.jpg" width="250">
