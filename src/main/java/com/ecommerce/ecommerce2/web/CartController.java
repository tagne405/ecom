package com.ecommerce.ecommerce2.web;

import java.security.Principal;

import com.ecommerce.ecommerce2.Entity.*;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.ecommerce2.Service.CustomerService;
import com.ecommerce.ecommerce2.Service.ProduitService;
import com.ecommerce.ecommerce2.Service.ShoppingCartService;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CartController {

    private final CustomerService customerService;

    private final ShoppingCartService cartService;

    private final ProduitService produitService;

    public CartController(CustomerService customerService, ShoppingCartService cartService, ProduitService produitService) {
        this.customerService = customerService;
        this.cartService = cartService;
        this.produitService = produitService;
    }


    @PostMapping("/mesomb")
    public ResponseEntity<String> testmesomb(@RequestParam("payer") String payer,
                                             @RequestParam("amount") String amount,
                                             @RequestParam("fees") Boolean fees,
                                             @RequestParam("service") String service,
                                             @RequestParam("country") String country,
                                             @RequestParam("currency") String currency){

        String apiUrl = "https://mesomb.hachther.com/api/v1.1/payment/online/";
        String authorizationToken = "cc48313a-c2d2-4b8f-82d5-64a4e405505e";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authorizationToken);

        MesombPayRequestData requestData1 = new MesombPayRequestData(payer, amount, fees, service, country, currency);
        HttpEntity<MesombPayRequestData> requestEntity = new HttpEntity<>(requestData1, headers);



        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestData1, String.class);

//        if (responseEntity.getStatusCode().is2xxSuccessful()) {
//            String response = responseEntity.getBody();
//            System.out.println(response);
//        } else {
//            System.out.println("Payment request failed.");
//        }

        return responseEntity;
    }



    @PostMapping("/testons")
    public ModelAndView processPayment(@RequestParam("user_email") String userEmail,
                                       @RequestParam("amount") String amount,
                                       @RequestParam("cart_reference") String cartReference,
                                       @RequestParam("currency") String currency,
                                       @RequestParam("callback") String callback
                                       ) {

        String apiUrl = "https://api.notchpay.co/payments/initialize";
        String publicKey = "sb.kMUqExOBxstY0CC6IAj5b3letgDVLMqqb5IPqnsSFL9qQ9aLd6Iq0EoNL5ReLQz21CWlcFKahIuPoRbmESwXF6RK2sdCTZEGwDLM2WUhvzeovtOhrNPxk2ILISAVg"; // Remplacez par votre clé publique NotchPay

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", publicKey);

        // Création du corps de la requête
        NotchPayRequestData requestData = new NotchPayRequestData(userEmail, amount, cartReference, currency, callback);
        HttpEntity<NotchPayRequestData> requestEntity = new HttpEntity<>(requestData, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<NotchPayResponseData> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                NotchPayResponseData.class
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String authorizationUrl = responseEntity.getBody().getAuthorization_url();
            return new ModelAndView("redirect:"+authorizationUrl);
        } else {
            // Gérer les erreurs si nécessaire
            return new ModelAndView("redirect:/payment-failure"); // Rediriger vers une page d'échec de paiement
        }
    }

    // Classe pour représenter les données de la requête à NotchPay


    // Classe pour représenter les données de réponse de NotchPay
//    private static class NotchPayResponseData {
//        private String authorization_url;
//
//        public NotchPayResponseData() {
//            // Constructeur par défaut requis pour la désérialisation JSON
//        }
//
//        public String getAuthorization_url() {
//            return authorization_url;
//        }
//
//        public void setAuthorization_url(String authorization_url) {
//            this.authorization_url = authorization_url;
//        }
//    }


    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpSession session){

        if(principal == null){
            return "redirect:/login";
        }
        Customer customer = customerService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = customer.getShoppingcart();
        if(shoppingCart == null){
            model.addAttribute("check", "FOULE KAN Y'a rien dans ton Panier");    
        }
        if(shoppingCart != null){
        session.setAttribute("totalItem", shoppingCart.getTotalItems());
        double subTotal = shoppingCart.getPrixTotal();
        model.addAttribute("subTotal", subTotal);
        model.addAttribute("shoppingCart", shoppingCart);}
        return "cart";
    }


//    @PostMapping("/notPayment")
//    public ResponseEntity<Void> payement(){
//        String url = "https://api.notchpay.co/payments/initialize";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "sb.kMUqExOBxstY0CC6IAj5b3letgDVLMqqb5IPqnsSFL9qQ9aLd6Iq0EoNL5ReLQz21CWlcFKahIuPoRbmESwXF6RK2sdCTZEGwDLM2WUhvzeovtOhrNPxk2ILISAVg");
//        headers.setCacheControl("no-cache");
//
//        UUID nbre = UUID.randomUUID();
//        Map<String, String> data = new HashMap<>();
//        data.put("email", "client@email.com");
//        data.put("amount", "40");
//        data.put("currency", "XAF");
//        data.put("description", "je poura aussi description");
//        data.put("reference", "autre_reference"+nbre);
//        data.put("callback", "http://localhost:8085/payment/callback");
//        data.put("redirectUrl","http://localhost:8085/cart");
//
//        HttpEntity<Map<String, String>> request = new HttpEntity<>(data, headers);
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
//
//        // Récupérer le corps de la réponse
//        String responseBody = response.getBody();
//        System.out.println("Corps de la réponse: " + responseBody);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            JsonNode jsonNode = objectMapper.readTree(responseBody);
//            String authorizationUrl = jsonNode.get("authorization_url").asText();
//            System.out.println("Authorization_url: " + authorizationUrl);
//
//            HttpHeaders redirectHeaders = new HttpHeaders();
//            redirectHeaders.setLocation(URI.create(authorizationUrl));
//
//            return new ResponseEntity<>(redirectHeaders, HttpStatus.FOUND);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//
//    }

//        String url2 = "https://api.notchpay.co/payments/cTXHSX223NhUzjgxAJoRAzVFGwBIn0Kv";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "PUBLIC_KEY");
//        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
//
//        Map<String, Object> requestData = new HashMap<>();
//        requestData.put("channel", "cm.orange");
//        Map<String, Object> data = new HashMap<>();
//        data.put("phone", "+237653247334");
//        requestData.put("data", data);
//
//        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestData, headers);
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> responseEntity = restTemplate.exchange(url2, HttpMethod.PUT, requestEntity, String.class);
//
//        String responseBody2 = responseEntity.getBody();
//        System.out.println(responseBody2);





    // Endpoint Ajax pour récupérer le nombre total d'articles
    @GetMapping("/cart/nbpanier")
    @ResponseBody
    public int nbPannier(Model model,Principal principal, HttpSession session){

        Customer customer = customerService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = customer.getShoppingcart();
        if(shoppingCart == null){
            return 0;
        }else{

        return shoppingCart.getTotalItems();

        }
    }
    // Endpoint Ajax pour récupérer le nombre total d'articles
//    @GetMapping("/cart/total-items")
//    @ResponseBody
//    public int getTotalItems(HttpSession session) {
//
//
//        ShoppingCart shoppingCart = (ShoppingCart)session.getAttribute("shoppingCart");
//        if (shoppingCart != null) {
//            return shoppingCart.getTotalItems();
//        } else {
//            return 0;
//        }
//    }


//    @PostMapping("/addToCart{id}")
//    public String addToCart(@PathVariable("id")Long produitId,
//                            @RequestParam(value = "quantite", required = false, defaultValue = "1") int quantite,
//                            HttpServletRequest request,
//                            Principal principal,
//                            Model model,
//                            RedirectAttributes redirectAttributes){
//
//        if(principal == null){
//            return "Redirect:/login";
//        }
//        Produit produit = produitService.getProduitById(produitId);
//        String username = principal.getName();
//        Customer customer = customerService.findByUsername(username);
//
//        ShoppingCart cart = cartService.addItemToCart(produit, quantite, customer);
//
//        // redirige vers la page precedente vers laquelle cet requete a ete emise
////        return "redirect:"+ request.getHeader("Referer");
//        cart.getTotalItems();
//        redirectAttributes.addFlashAttribute("success","Ajout au panier reussie Vas voir si tu veut");
//        return "valide";
//    }



    @GetMapping("/addToCart/{id}")
    public ResponseEntity<String> addToCart(@PathVariable("id") Long produitId,
                                            @RequestParam(value = "quantite", required = false, defaultValue = "1") int quantite,
                                            Principal principal) {


        if(principal == null){
            // Si l'utilisateur n'est pas connecté, retourne une réponse indiquant qu'il doit se connecter
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Vous devez être connecté pour ajouter un produit au panier.");
        }

        Produit produit = produitService.getProduitById(produitId);
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);

        ShoppingCart cart = cartService.addItemToCart(produit, quantite, customer);

        // Retourne une réponse indiquant que l'ajout au panier a réussi et le nombre total d'articles dans le panier
        return ResponseEntity.ok("" + cart.getTotalItems());
    }



    @RequestMapping(value = "/update-cart/{id}/{prixvente}/{quantite}", method = RequestMethod.GET)
    public String updateCart(@PathVariable("quantite")int quantite,@PathVariable("prixvente")int prixvente,
                            @PathVariable("id") Long produitId,
                            Model model,
                            Principal principal ){
        if(principal == null){
            return "redirect:/login";
        }else{
            String username = principal.getName();
            Customer customer = customerService.findByUsername(username);
            Produit produit = produitService.getProduitById(produitId);

            ShoppingCart cart = cartService.updateItemInCart(produit, quantite, customer);

            model.addAttribute("shoppingCart", cart);
            return "redirect:/cart";
        }
    }

    @GetMapping("/prixTotal")
    @ResponseBody
    public double getTotalPrice(Model model,Principal principal, HttpSession session){
        Customer customer = customerService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = customer.getShoppingcart();
        if(shoppingCart == null){
            return 0;
        }else{
            return shoppingCart.getPrixTotal();
        }
    }

    //envoi par la methode post
   @RequestMapping(value = "/delete-cart/{id}",  method = {RequestMethod.GET,RequestMethod.DELETE})
   public String deleteItemFromCart(@PathVariable("id")Long produitId,
                                    Model model,
                                    Principal principal,
                                    RedirectAttributes redirectAttributes){
        System.out.println(produitId);
        if(principal == null){
            return ("Vous devez être connecté pour Spprimer un produit au panier.");
        }else{
            String username = principal.getName();
            Customer customer = customerService.findByUsername(username);
            Produit produit = produitService.getProduitById(produitId);
            ShoppingCart cart = cartService.deleteItemFromCart(produit, customer);
            model.addAttribute("shoppingCart", cart);
            redirectAttributes.addFlashAttribute("delete","supperssion Reussie");
            return "redirect:/cart";

        }

   }
}
