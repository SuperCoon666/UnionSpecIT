# serve.ps1
nvm use 20.1.0
$nodePath = nvm which current
$env:PATH = [System.IO.Path]::GetDirectoryName($nodePath) + ";" + $env:PATH
$env:NODE_OPTIONS="--openssl-legacy-provider"
npm run serve-only
