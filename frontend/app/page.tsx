"use client"

import { useEffect, useRef } from "react"

export default function Home() {
  const wsRef = useRef<WebSocket | null>(null)

  useEffect(() => {
    const ws = new WebSocket("ws://localhost:8080/ws/events")
    wsRef.current = ws

    ws.onopen = () => {
        console.log("websocket connected")
      sendEvent("page_view", { page: "/" })
    }

    return () => {
      ws.close()
    }
  }, [])

  function sendEvent(type: string, data: any) {
    if (!wsRef.current) return

    wsRef.current.send(
      JSON.stringify({
        type,
        ...data,
        ts: Date.now()
      })
    )
  }

  return (
    <main style={{ padding: 40 }}>
      <h1>User Behaviour Tracking</h1>

      <button className="button"
        onClick={() =>
          sendEvent("click", {
            page: "/",
            element: "track-button"
          })
        }
      >
        Track Click
      </button>
    </main>
  )
}
