﻿using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using AUT03_04_AndresIzquierdo_JuegosAuthAPI.Models;

namespace AUT03_04_AndresIzquierdo_JuegosAuthAPI.Controllers
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

        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status401Unauthorized)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status500InternalServerError)]
        [HttpPost("login")]
        public async Task<IActionResult> Login([FromBody] User user)
        {
            var existingUser = await _userManager.FindByNameAsync(user.Email);

            // Verifica si el usuario existe
            if (existingUser == null)
                return NotFound();

            // Verifica si la contraseña es correcta
            if (!await _userManager.CheckPasswordAsync(existingUser, user.Password))
                return Unauthorized();

            return Ok(await CreateToken(existingUser)); // Crea el token JWT para el usuario
        }

        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status401Unauthorized)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status500InternalServerError)]
        [HttpPost("register")]
        public async Task<IActionResult> Register([FromBody] User user)
        {
            var userExists = await _userManager.FindByNameAsync(user.Email); // Busca al usuario en el userManager por nombre

            // Verifica si el usuario existe
            if (userExists != null)
                return StatusCode(StatusCodes.Status500InternalServerError, new { Status = "Error", Message = "User already exists!" });

            IdentityUser newUser = new IdentityUser
            {
                UserName = user.Email
            };

            // Crea el usuario y en el caso que no sea exitoso, devuelve un error
            var result = await _userManager.CreateAsync(newUser, user.Password);
            if (!result.Succeeded)
                return StatusCode(StatusCodes.Status500InternalServerError, new { Status = "Error", Message = "User creation failed! Please check user details and try again." });

            // Le agrega el rol de User al nuevo usuario creado
            await _userManager.AddToRoleAsync(newUser, "User");


            return Ok(new { Status = "Success", Message = "User created successfully!" });
        }

        private async Task<string> CreateToken(IdentityUser user)
        {
            var issuedAt = new DateTimeOffset(DateTime.UtcNow).ToUnixTimeSeconds();

            var claims = new List<Claim>
            {
                //Identificador único del token
                new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString()),
                //Fecha de emisión del token
                new Claim(JwtRegisteredClaimNames.Iat, issuedAt.ToString()),
                //Usuario portador del token
                new Claim(JwtRegisteredClaimNames.Sub, user.UserName)
            };
            var userRoles = await _userManager.GetRolesAsync(user);
            foreach (var userRole in userRoles)
                claims.Add(new Claim("Role", userRole));

            var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_configuration["JWT:Key"]));
            var credentials = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);

            var token = new JwtSecurityToken(
            _configuration["JWT:Issuer"],
            _configuration["JWT:Audience"],
            claims,
            expires: DateTime.UtcNow.AddMinutes(10), // modifiquen el tiempo de duración del token
            signingCredentials: credentials);

            return new JwtSecurityTokenHandler().WriteToken(token);
        }
    }
}
