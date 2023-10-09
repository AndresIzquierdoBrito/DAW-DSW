using Microsoft.AspNetCore.Mvc;

namespace First_MVC_Test.Controllers
{
    public class MyController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }

        public string Greetings()
        {
            return "Hey there!";
        }

        public string Goodbye()
        {
            return "See you later";
        }
    }
}
