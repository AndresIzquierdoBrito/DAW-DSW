using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using UT03_Ej02_AndresIzquierdo.Models;

namespace UT03_Ej02_AndresIzquierdo.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AuthController : ControllerBase
    {
        private readonly UserManager<IdentityUser> _userManager;
        private readonly RoleManager<IdentityRole> _roleManager;
        private readonly IConfiguration _configuration;

        public AuthController(
            UserManager<IdentityUser> userManager,
            RoleManager<IdentityRole> roleManager,
            IConfiguration configuration
        )
        {
            _userManager = userManager;
            _roleManager = roleManager;
            _configuration = configuration;
        }

        [HttpPost("login")]
        public IActionResult Login([FromBody] User user)
        {

            
            return Ok(CreateToken(new IdentityUser { }));
        }

        private async Task<string> CreateToken(IdentityUser user)
        {
            var claims = new List<Claim>
            {
                //Identificador único del token
                new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString()),
                //Fecha de emisión del token
                new Claim(JwtRegisteredClaimNames.Iat, DateTime.UtcNow.ToString()),
                //Usuario portador del token
                new Claim(JwtRegisteredClaimNames.Sub, user.Email)
            };

            var userRoles = await _userManager.GetRolesAsync(user);

            foreach (var userRole in userRoles)
            {
                claims.Add(new Claim("Role", userRole));
            }

            var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_configuration["JWT:Key"]));
            var credentials = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);
            var token = new JwtSecurityToken(
            _configuration["Jwt:Issuer"],
            _configuration["Jwt:Audience"],
            claims,
            expires: DateTime.UtcNow.AddMinutes(10), // modifiquen el tiempo de duración del token
            signingCredentials: credentials);

            return new JwtSecurityTokenHandler().WriteToken(token);
        }

        //[HttpPost("register")]
        //public IActionResult Register([FromBody] User user)
        //{

        //}

    }


}
