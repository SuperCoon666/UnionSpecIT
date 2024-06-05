# build.ps1
nvm use 16.15.1
$nodePath = nvm which current
$npmPath = [System.IO.Path]::Combine([System.IO.Path]::GetDirectoryName($nodePath), "node_modules", ".bin")
$env:PATH = "$npmPath;$env:PATH"
npm run build-only
npm run copy
