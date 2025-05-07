{ pkgs ? import <nixpkgs> {} }:
pkgs.mkShell {
  buildInputs = [
    pkgs.openal
    pkgs.alsa-lib
    pkgs.alsa-utils
    pkgs.pulseaudio
  ];

  shellHook = ''
    export LD_LIBRARY_PATH="${pkgs.openal}/lib:''${LD_LIBRARY_PATH:-}"
    export LD_LIBRARY_PATH="${pkgs.alsa-lib}/lib:''${LD_LIBRARY_PATH}"
    export LD_LIBRARY_PATH="${pkgs.pulseaudio}/lib:''${LD_LIBRARY_PATH}"
    export LD_LIBRARY_PATH="${pkgs.libglvnd}/lib:''${LD_LIBRARY_PATH}"
  '';
}