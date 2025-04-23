@echo off
powershell -NoProfile -ExecutionPolicy Bypass -Command "Get-Process chrome,chromedriver -ErrorAction SilentlyContinue | Stop-Process -Force"
