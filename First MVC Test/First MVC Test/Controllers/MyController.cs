using First_MVC_Test.Models;
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

        public IActionResult Perfil()
        {
            Person persona = new Person
            {
                Id = 1,
                Name = "Andrew",
                Age = 21,
                Email = "andres.izbri@gmail.com"
            };


            return View(persona);
        }

        //public IActionResult Felicita(int id, string name, int age)
        //{
        //    ViewData["ID"] = id;
        //    ViewData["name"] = name;
        //    ViewData["age"] = age;

        //    return View();
        //}

        public IActionResult Felicita()
        {
            Person persona = new Person
            {
                Id = 1,
                Name = "Andrew",
                Age = 21,
                Email = "andres.izbri@gmail.com"
            };


            return View(persona);
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
