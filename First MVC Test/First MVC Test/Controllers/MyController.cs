using Microsoft.AspNetCore.Mvc;

namespace First_MVC_Test.Controllers
{
    public class MyController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }
        public IActionResult Goodbye()
        {
            return View();
        }

        public IActionResult Felicita(int id, string name, int age)
        {
            ViewData["ID"] = id;
            ViewData["name"] = name;
            ViewData["age"] = age;

            return View();
        }
       

        public string Greetings()
        {
            return "Hey there!";
        }

        public IActionResult actionName()
        {
            ViewData["name"] = "Johnson";
            ViewData["nameTimes"] = 3;
            return View();
        }
        //public string Goodbye(int id, string name, int age)
        //{
        //    return $"El usuario con ID: {id} y nombre {name} se despisde a sus {age} años. DEP";
        //}

        //public string actionName(int ID, int year)
        //{
        //    return "El usuario con ID " + ID + " es del año: " + year;
        //}

    }
}
