using AUT02_02_IzquierdoAndres_ModernFamily.Models;
using Microsoft.AspNetCore.Mvc;

namespace AUT02_02_IzquierdoAndres_ModernFamily.Controllers
{
    public class PersonajeController : Controller
    {
        static List<Personaje> characters = new List<Personaje>
        {
            new Personaje { Id = 1, Name = "Jay Pritchett", Family = "Pritchett", NChildren = 3 },
            new Personaje { Id = 2, Name = "Gloria Pritchett", Family = "Pritchett", NChildren = 1 },
            new Personaje { Id = 3, Name = "Phil Dunphy", Family = "Dunphy", NChildren = 3 },
            new Personaje { Id = 4, Name = "Claire Dunphy", Family = "Dunphy", NChildren = 3 },
            new Personaje { Id = 5, Name = "Mitchell Pritchett", Family = "Pritchett", NChildren = 1 },
            new Personaje { Id = 6, Name = "Cameron Tucker", Family = "Pritchett", NChildren = 1 },
            new Personaje { Id = 7, Name = "Manny Delgado", Family = "Pritchett", NChildren = 0 },
            new Personaje { Id = 8, Name = "Alex Dunphy", Family = "Dunphy", NChildren = 0 }
        };

        public IActionResult Index() => View(characters);

         [HttpGet]  //Se llama a este método en el caso que la página devuelva haga una petición GET
        public IActionResult Create() => View();

        [HttpPost]  //Se llama a este método en el caso que la página devuelva haga una petición POST
        [ValidateAntiForgeryToken]
        public IActionResult Create(Personaje character)
        {
            if (ModelState.IsValid)
            {
                character.Id = characters.Count > 0 ? characters.Last().Id + 1 : 1;
                characters.Add(character);
            }

            return RedirectToAction("LastCharacterDetail");
        }

        public IActionResult Details(int id) => View(characters[id - 1]);

        public IActionResult LastCharacterDetail()
        {
            return View(characters.Last());
        }

    }
}
